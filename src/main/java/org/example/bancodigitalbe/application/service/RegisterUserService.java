package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.UsernameAlreadyExistsException;
import org.example.bancodigitalbe.domain.model.NewUser;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.RegisterUserCommand;
import org.example.bancodigitalbe.domain.port.in.RegisterUserUseCase;
import org.example.bancodigitalbe.domain.port.out.SaveNewUserPort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final SaveNewUserPort saveNewUserPort;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(SaveNewUserPort saveNewUserPort, PasswordEncoder passwordEncoder) {
        this.saveNewUserPort = saveNewUserPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterUserCommand command) throws UsernameAlreadyExistsException {

        NewUser newUser = commandToNewUser(command);

        return saveNewUserPort.save(newUser);
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
