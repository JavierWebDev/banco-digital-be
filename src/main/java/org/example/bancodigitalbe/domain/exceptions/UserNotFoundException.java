package org.example.bancodigitalbe.domain.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final String username;

    public UserNotFoundException(String username) {
        super("[!] username {" + username + "} not found");
        this.username = username;
    }

}
