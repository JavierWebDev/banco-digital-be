package org.example.bancodigitalbe.domain.port.in;

import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
public class UpdatePersonalDataCommand {

    private final String username;

    // a null field means "leave this one as it is"
    private final String email;

    private final String phone;

    private final String address;

    public UpdatePersonalDataCommand(String username, String email, String phone, String address) {
        this.username = requireNonNull(username, "[!] username cant be null");
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}
