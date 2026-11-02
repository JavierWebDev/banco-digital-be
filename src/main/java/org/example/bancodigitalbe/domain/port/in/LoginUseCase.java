package org.example.bancodigitalbe.domain.port.in;

import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.User;

public interface LoginUseCase {
    User login(String username) throws UserNotFoundException;
}
