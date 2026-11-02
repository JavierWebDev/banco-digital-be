package org.example.bancodigitalbe.domain.port.in;

import org.example.bancodigitalbe.domain.exceptions.UsernameAlreadyExistsException;
import org.example.bancodigitalbe.domain.model.User;

public interface RegisterUserUseCase {
    User register(RegisterUserCommand command) throws UsernameAlreadyExistsException;
}
