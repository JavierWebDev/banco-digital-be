package org.example.bancodigitalbe.domain.port.in;

import org.example.bancodigitalbe.domain.exceptions.InvalidCurrentCredentialException;
import org.example.bancodigitalbe.domain.exceptions.InvalidPinFormatException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;

public interface SetPinUseCase {
    void setPin(SetPinCommand command)
            throws UserNotFoundException, InvalidCurrentCredentialException, InvalidPinFormatException;
}
