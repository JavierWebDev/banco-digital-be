package org.example.bancodigitalbe.domain.exceptions;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AmountNotDispensableException extends RuntimeException {
    private final BigDecimal amount;

    public AmountNotDispensableException(BigDecimal amount) {
        super("[!] el monto solicitado {" + amount + "} no puede entregarse de forma exacta con los"
                + " billetes disponibles de $100.000, $50.000, $20.000 y $10.000 COP");
        this.amount = amount;
    }

}
