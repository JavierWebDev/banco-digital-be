package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.InvalidCurrentCredentialException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.ChangePasswordCommand;
import org.example.bancodigitalbe.domain.port.in.ChangePasswordUseCase;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.example.bancodigitalbe.domain.port.out.UpdateUserCredentialsPort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangePasswordService implements ChangePasswordUseCase {

    private final LoadUserPort loadUserPort;
    private final UpdateUserCredentialsPort updateUserCredentialsPort;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordService(LoadUserPort loadUserPort, UpdateUserCredentialsPort updateUserCredentialsPort,
                                 PasswordEncoder passwordEncoder) {
        this.loadUserPort = loadUserPort;
        this.updateUserCredentialsPort = updateUserCredentialsPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordCommand command)
            throws UserNotFoundException, InvalidCurrentCredentialException {

        User user = loadUserPort.loadByUsername(command.getUsername())
                .orElseThrow(() -> new UserNotFoundException(command.getUsername()));

        // HU-04 escenario 2: la clave actual tiene que coincidir
        if (!passwordEncoder.matches(command.getCurrentPassword(), user.getPassword())) {
            throw new InvalidCurrentCredentialException("password");
        }

        updateUserCredentialsPort.updatePassword(
                user.getUserId(), passwordEncoder.encode(command.getNewPassword()));
    }

}
