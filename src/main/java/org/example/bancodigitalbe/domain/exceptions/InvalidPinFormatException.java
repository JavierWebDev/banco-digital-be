package org.example.bancodigitalbe.domain.exceptions;

public class InvalidPinFormatException extends RuntimeException {

    public InvalidPinFormatException() {
        super("[!] el PIN debe componerse exactamente de 4 digitos");
    }

}
