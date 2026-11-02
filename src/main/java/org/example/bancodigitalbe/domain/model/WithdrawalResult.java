package org.example.bancodigitalbe.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Getter
public class WithdrawalResult {

    private final Transaction transaction;

    private final List<BillBundle> bills;

    private final BigDecimal withdrawnToday;

    public WithdrawalResult(Transaction transaction, List<BillBundle> bills, BigDecimal withdrawnToday) {
        this.transaction = requireNonNull(transaction, "[!] transaction cant be null");
        this.bills = requireNonNull(bills, "[!] bills cant be null");
        this.withdrawnToday = requireNonNull(withdrawnToday, "[!] withdrawnToday cant be null");
    }

}
