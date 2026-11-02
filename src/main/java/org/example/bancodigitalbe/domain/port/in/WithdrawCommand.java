package org.example.bancodigitalbe.domain.port.in;

import lombok.Getter;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

@Getter
public class WithdrawCommand {

    private final String username;

    private final BigDecimal amount;

    private final String pin;

    public WithdrawCommand(String username, BigDecimal amount, String pin) {
        this.username = requireNonNull(username, "[!] username cant be null");
        this.amount = requireNonNull(amount, "[!] amount cant be null");
        this.pin = pin;
    }
}
