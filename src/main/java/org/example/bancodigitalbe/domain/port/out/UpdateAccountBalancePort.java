package org.example.bancodigitalbe.domain.port.out;

import org.example.bancodigitalbe.domain.model.Account;

import java.math.BigDecimal;

public interface UpdateAccountBalancePort {
    Account updateBalance(Long accountId, BigDecimal newBalance);
}
