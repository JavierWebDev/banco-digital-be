package org.example.bancodigitalbe.domain.exceptions;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {
    private final Long userId;

    public AccountNotFoundException(Long userId) {
        super("[!] user {" + userId + "} has no bank account");
        this.userId = userId;
    }

}
