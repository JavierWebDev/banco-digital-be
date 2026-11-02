package org.example.bancodigitalbe.domain.port.out;

import org.example.bancodigitalbe.domain.model.User;

import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadByUsername(String user);
}
