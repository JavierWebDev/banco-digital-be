package org.example.bancodigitalbe.domain.exceptions;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class InsufficientFundsException extends RuntimeException {
    private final BigDecimal amount;
    private final BigDecimal balance;

    public InsufficientFundsException(BigDecimal amount, BigDecimal balance) {
        super("[!] el monto solicitado {" + amount + "} supera el saldo disponible {" + balance + "}");
        this.amount = amount;
        this.balance = balance;
    }

}
