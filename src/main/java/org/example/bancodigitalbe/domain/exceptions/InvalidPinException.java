package org.example.bancodigitalbe.domain.exceptions;

public class InvalidPinException extends RuntimeException {

    public InvalidPinException() {
        super("[!] el PIN de seguridad es incorrecto");
    }

}
