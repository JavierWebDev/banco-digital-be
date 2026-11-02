package org.example.bancodigitalbe.domain.port.out;

import org.example.bancodigitalbe.domain.model.Account;

public interface CreateAccountPort {
    Account createForUser(Long userId);
}
