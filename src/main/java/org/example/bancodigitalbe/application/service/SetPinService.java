package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.InvalidCurrentCredentialException;
import org.example.bancodigitalbe.domain.exceptions.InvalidPinFormatException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.SecurityPin;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.SetPinCommand;
import org.example.bancodigitalbe.domain.port.in.SetPinUseCase;
import org.example.bancodigitalbe.domain.port.out.LoadUserPinPort;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.example.bancodigitalbe.domain.port.out.UpdateUserCredentialsPort;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SetPinService implements SetPinUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadUserPinPort loadUserPinPort;
    private final UpdateUserCredentialsPort updateUserCredentialsPort;
    private final PasswordEncoder passwordEncoder;

    public SetPinService(LoadUserPort loadUserPort, LoadUserPinPort loadUserPinPort,
                         UpdateUserCredentialsPort updateUserCredentialsPort, PasswordEncoder passwordEncoder) {
        this.loadUserPort = loadUserPort;
        this.loadUserPinPort = loadUserPinPort;
        this.updateUserCredentialsPort = updateUserCredentialsPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void setPin(SetPinCommand command)
            throws UserNotFoundException, InvalidCurrentCredentialException, InvalidPinFormatException {

        // HU-04 escenario 2: un PIN de longitud distinta a 4 digitos se rechaza
        SecurityPin.requireValidFormat(command.getNewPin());

        User user = loadUserPort.loadByUsername(command.getUsername())
                .orElseThrow(() -> new UserNotFoundException(command.getUsername()));

        Optional<String> currentEncodedPin = loadUserPinPort.loadEncodedPin(user.getUserId());

        // the first PIN is an activation, so there is nothing to confirm yet;
        // from then on the current PIN is required to replace it
        if (currentEncodedPin.isPresent()
                && (command.getCurrentPin() == null
                || !passwordEncoder.matches(command.getCurrentPin(), currentEncodedPin.get()))) {
            throw new InvalidCurrentCredentialException("pin");
        }

        updateUserCredentialsPort.updatePin(
                user.getUserId(), passwordEncoder.encode(command.getNewPin()));
    }

}
