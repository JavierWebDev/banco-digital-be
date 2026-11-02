package org.example.bancodigitalbe.domain.exceptions;

import lombok.Getter;

@Getter
public class UsernameAlreadyExistsException extends RuntimeException {
    private final String username;

    public UsernameAlreadyExistsException(String username, Exception cause) {
        super("[!] username {"+ username + "} already exists", cause);
        this.username = username;
    }

}
