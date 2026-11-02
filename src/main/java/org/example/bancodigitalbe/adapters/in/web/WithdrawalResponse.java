package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.model.BillBundle;
import org.example.bancodigitalbe.domain.model.WithdrawalResult;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record WithdrawalResponse(
        Long transactionId,
        BigDecimal amount,
        BigDecimal balanceAfter,
        String currency,
        List<BillBundle> bills,
        int totalBills,
        BigDecimal withdrawnToday,
        Instant createdAt,
        String message
) {
    private static final String CURRENCY = "COP";

    public static WithdrawalResponse fromDomainResult(WithdrawalResult result) {
        return new WithdrawalResponse(
                result.getTransaction().getTransactionId(),
                result.getTransaction().getAmount(),
                result.getTransaction().getBalanceAfter(),
                CURRENCY,
                result.getBills(),
                result.getBills().stream().mapToInt(BillBundle::quantity).sum(),
                result.getWithdrawnToday(),
                result.getTransaction().getCreatedAt(),
                "[+] retiro procesado correctamente"
        );
    }
}
