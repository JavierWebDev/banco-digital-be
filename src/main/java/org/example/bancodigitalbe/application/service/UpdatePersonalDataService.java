package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.InvalidPersonalDataException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.UpdatePersonalDataCommand;
import org.example.bancodigitalbe.domain.port.in.UpdatePersonalDataUseCase;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.example.bancodigitalbe.domain.port.out.UpdateUserPersonalDataPort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePersonalDataService implements UpdatePersonalDataUseCase {

    private final LoadUserPort loadUserPort;
    private final UpdateUserPersonalDataPort updateUserPersonalDataPort;

    public UpdatePersonalDataService(LoadUserPort loadUserPort, UpdateUserPersonalDataPort updateUserPersonalDataPort) {
        this.loadUserPort = loadUserPort;
        this.updateUserPersonalDataPort = updateUserPersonalDataPort;
    }

    @Override
    @Transactional
    public User updatePersonalData(UpdatePersonalDataCommand command)
            throws UserNotFoundException, InvalidPersonalDataException {

        if (command.getEmail() == null && command.getPhone() == null && command.getAddress() == null) {
            throw new InvalidPersonalDataException("at least one of email, phone or address must be provided");
        }

        User user = loadUserPort.loadByUsername(command.getUsername())
                .orElseThrow(() -> new UserNotFoundException(command.getUsername()));

        // HU-03 escenario 1: solo se tocan los campos enviados
        return updateUserPersonalDataPort.updatePersonalData(
                user.getUserId(),
                command.getEmail() != null ? command.getEmail() : user.getEmail(),
                command.getPhone() != null ? command.getPhone() : user.getPhone(),
                command.getAddress() != null ? command.getAddress() : user.getAddress()
        );
    }

}
