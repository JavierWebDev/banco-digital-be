package org.example.bancodigitalbe.domain.port.out;

import org.example.bancodigitalbe.domain.exceptions.UsernameAlreadyExistsException;
import org.example.bancodigitalbe.domain.model.NewUser;
import org.example.bancodigitalbe.domain.model.User;

public interface SaveNewUserPort {
    User save(NewUser newUser) throws UsernameAlreadyExistsException;
}
