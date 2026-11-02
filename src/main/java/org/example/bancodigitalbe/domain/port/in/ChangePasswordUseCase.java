package org.example.bancodigitalbe.domain.port.in;

import org.example.bancodigitalbe.domain.exceptions.InvalidCurrentCredentialException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;

public interface ChangePasswordUseCase {
    void changePassword(ChangePasswordCommand command)
            throws UserNotFoundException, InvalidCurrentCredentialException;
}
