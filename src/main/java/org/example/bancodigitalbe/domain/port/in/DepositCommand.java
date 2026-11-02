package org.example.bancodigitalbe.domain.port.in;

import lombok.Getter;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

@Getter
public class DepositCommand {

    private final String username;

    private final BigDecimal amount;

    public DepositCommand(String username, BigDecimal amount) {
        this.username = requireNonNull(username, "[!] username cant be null");
        this.amount = requireNonNull(amount, "[!] amount cant be null");
    }
}
