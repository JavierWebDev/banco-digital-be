package org.example.bancodigitalbe.domain.port.in;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class SetPinCommand {

    private final String username;

    // only required once a PIN has already been activated
    private final String currentPin;

    private final String newPin;

    public SetPinCommand(String username, String currentPin, String newPin) {
        this.username = requireNonNull(username, "[!] username cant be null");
        this.currentPin = currentPin;
        this.newPin = requireNonNull(newPin, "[!] newPin cant be null");
    }
}
