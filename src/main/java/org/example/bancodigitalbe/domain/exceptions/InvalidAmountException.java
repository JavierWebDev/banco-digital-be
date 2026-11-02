package org.example.bancodigitalbe.domain.exceptions;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class InvalidAmountException extends RuntimeException {
    private final BigDecimal amount;

    public InvalidAmountException(BigDecimal amount) {
        super("[!] amount {" + amount + "} must be greater than zero");
        this.amount = amount;
    }

}
