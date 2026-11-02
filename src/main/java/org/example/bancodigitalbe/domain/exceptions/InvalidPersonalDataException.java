package org.example.bancodigitalbe.domain.exceptions;

public class InvalidPersonalDataException extends RuntimeException {

    public InvalidPersonalDataException(String reason) {
        super("[!] " + reason);
    }

}
