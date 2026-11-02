package org.example.bancodigitalbe.domain.port.out;

import org.example.bancodigitalbe.domain.model.Account;

import java.util.Optional;

public interface LoadAccountPort {
    Optional<Account> loadByUserId(Long userId);
}
