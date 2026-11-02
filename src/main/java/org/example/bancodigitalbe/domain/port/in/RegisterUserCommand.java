package org.example.bancodigitalbe.domain.port.in;

import lombok.Getter;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

@Getter
public class RegisterUserCommand {

    private final String user;

    private final String password;

    // CuentaBancaria cuentaBancaria;

    private final String name;

    private final String lastName;

    private final LocalDate birthDate;

    public RegisterUserCommand(String user, String password, String name, String lastName, LocalDate birthDate) {
        this.user = requireNonNull(user, "[!] user cant be null");
        this.password = requireNonNull(password, "[!] password cant be null");
        this.name = requireNonNull(name, "[!] name cant be null");
        this.lastName = requireNonNull(lastName, "[!] lastName cant be null");
        this.birthDate = requireNonNull(birthDate, "[!] birthDate cant be null");
    }
}
