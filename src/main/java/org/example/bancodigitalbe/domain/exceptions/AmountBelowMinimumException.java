package org.example.bancodigitalbe.domain.exceptions;

import lombok.Getter;
import org.example.bancodigitalbe.domain.model.WithdrawalPolicy;

import java.math.BigDecimal;

@Getter
public class AmountBelowMinimumException extends RuntimeException {
    private final BigDecimal amount;

    public AmountBelowMinimumException(BigDecimal amount) {
        super("[!] el monto solicitado {" + amount + "} es inferior al minimo permitido de $"
                + WithdrawalPolicy.MINIMUM_AMOUNT + " COP");
        this.amount = amount;
    }

}
