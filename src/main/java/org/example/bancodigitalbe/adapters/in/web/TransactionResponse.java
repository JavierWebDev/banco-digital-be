package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
        Long transactionId,
        String type,
        BigDecimal amount,
        BigDecimal balanceAfter,
        String currency,
        Instant createdAt,
        String message
) {
    private static final String CURRENCY = "COP";

    public static TransactionResponse fromDomainTransaction(Transaction transaction, String message) {
        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getType().name(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                CURRENCY,
                transaction.getCreatedAt(),
                message
        );
    }
}
