package org.example.bancodigitalbe.domain.port.in;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class ChangePasswordCommand {

    private final String username;

    private final String currentPassword;

    private final String newPassword;

    public ChangePasswordCommand(String username, String currentPassword, String newPassword) {
        this.username = requireNonNull(username, "[!] username cant be null");
        this.currentPassword = requireNonNull(currentPassword, "[!] currentPassword cant be null");
        this.newPassword = requireNonNull(newPassword, "[!] newPassword cant be null");
    }
}
