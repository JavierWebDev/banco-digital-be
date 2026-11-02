package org.example.bancodigitalbe.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

@Getter
public class Account {
    private final Long accountId;

    private final Long userId;

    private final BigDecimal balance;

    public Account(Long accountId, Long userId, BigDecimal balance) {
        this.accountId = requireNonNull(accountId, "[!] accountId cant be null");
        this.userId = requireNonNull(userId, "[!] userId cant be null");
        this.balance = requireNonNull(balance, "[!] balance cant be null");
    }

}
