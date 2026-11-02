package org.example.bancodigitalbe.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

@Getter
public class NewTransaction {
    private final Long accountId;

    private final TransactionType type;

    private final BigDecimal amount;

    private final BigDecimal balanceAfter;

    public NewTransaction(Long accountId, TransactionType type, BigDecimal amount, BigDecimal balanceAfter) {
        this.accountId = requireNonNull(accountId, "[!] accountId cant be null");
        this.type = requireNonNull(type, "[!] type cant be null");
        this.amount = requireNonNull(amount, "[!] amount cant be null");
        this.balanceAfter = requireNonNull(balanceAfter, "[!] balanceAfter cant be null");
    }

}
