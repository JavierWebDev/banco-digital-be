package org.example.bancodigitalbe.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

import static java.util.Objects.requireNonNull;

@Getter
public class Transaction {
    private final Long transactionId;

    private final Long accountId;

    private final TransactionType type;

    private final BigDecimal amount;

    private final BigDecimal balanceAfter;

    private final Instant createdAt;

    public Transaction(Long transactionId, Long accountId, TransactionType type, BigDecimal amount, BigDecimal balanceAfter, Instant createdAt) {
        this.transactionId = requireNonNull(transactionId, "[!] transactionId cant be null");
        this.accountId = requireNonNull(accountId, "[!] accountId cant be null");
        this.type = requireNonNull(type, "[!] type cant be null");
        this.amount = requireNonNull(amount, "[!] amount cant be null");
        this.balanceAfter = requireNonNull(balanceAfter, "[!] balanceAfter cant be null");
        this.createdAt = requireNonNull(createdAt, "[!] createdAt cant be null");
    }

}
