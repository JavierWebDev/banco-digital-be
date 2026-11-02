package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.model.Account;

import java.math.BigDecimal;
import java.time.Instant;

public record BalanceResponse(
        Long accountId,
        BigDecimal balance,
        String currency,
        Instant consultedAt
) {
    private static final String CURRENCY = "COP";

    public static BalanceResponse fromDomainAccount(Account account) {
        return new BalanceResponse(
                account.getAccountId(),
                account.getBalance(),
                CURRENCY,
                Instant.now()
        );
    }
}
