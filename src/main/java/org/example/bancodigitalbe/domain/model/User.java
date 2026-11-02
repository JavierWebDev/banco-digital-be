package org.example.bancodigitalbe.domain.model;

import lombok.Getter;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

@Getter
public class User {
    private final Long userId;

    private final String user;

    private final String password;

    private final String name;

    private final String lastName;

    private final LocalDate birthDate;

    // contact data is not collected at registration, so it stays null until
    // the titular fills it in through the personal data module (RF04)
    private final String email;

    private final String phone;

    private final String address;

    public User(Long userId, String user, String password, String name, String lastName, LocalDate birthDate,
                String email, String phone, String address) {
        this.userId = requireNonNull(userId, "[!] userId cant be null");
        this.user = requireNonNull(user, "[!] user cant be null");
        this.password = requireNonNull(password, "[!] password cant be null");
        this.name = requireNonNull(name, "[!] name cant be null");
        this.lastName = requireNonNull(lastName, "[!] lastName cant be null");
        this.birthDate = requireNonNull(birthDate, "[!] birthDate cant be null");
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

}
