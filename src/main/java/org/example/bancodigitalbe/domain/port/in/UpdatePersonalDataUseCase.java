package org.example.bancodigitalbe.domain.port.in;

import org.example.bancodigitalbe.domain.exceptions.InvalidPersonalDataException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.User;

public interface UpdatePersonalDataUseCase {
    User updatePersonalData(UpdatePersonalDataCommand command)
            throws UserNotFoundException, InvalidPersonalDataException;
}
