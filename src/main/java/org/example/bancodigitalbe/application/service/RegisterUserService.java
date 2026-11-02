package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.UsernameAlreadyExistsException;
import org.example.bancodigitalbe.domain.model.NewUser;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.RegisterUserCommand;
import org.example.bancodigitalbe.domain.port.in.RegisterUserUseCase;
import org.example.bancodigitalbe.domain.port.out.CreateAccountPort;
import org.example.bancodigitalbe.domain.port.out.SaveNewUserPort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final SaveNewUserPort saveNewUserPort;
    private final CreateAccountPort createAccountPort;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(SaveNewUserPort saveNewUserPort, CreateAccountPort createAccountPort, PasswordEncoder passwordEncoder) {
        this.saveNewUserPort = saveNewUserPort;
        this.createAccountPort = createAccountPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User register(RegisterUserCommand command) throws UsernameAlreadyExistsException {

        NewUser newUser = commandToNewUser(command);

        User user = saveNewUserPort.save(newUser);

        // every user owns exactly one account, opened with a balance of $0 COP
        createAccountPort.createForUser(user.getUserId());

        return user;
    }

    private NewUser commandToNewUser(RegisterUserCommand command) {
        return new NewUser(
                command.getUser(),
                encodePassword(command.getPassword()),
                command.getName(),
                command.getLastName(),
                command.getBirthDate()
        );
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
