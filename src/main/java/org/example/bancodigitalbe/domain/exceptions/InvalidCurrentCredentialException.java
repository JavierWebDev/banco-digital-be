package org.example.bancodigitalbe.domain.exceptions;

public class InvalidCurrentCredentialException extends RuntimeException {

    public InvalidCurrentCredentialException(String credential) {
        super("[!] the current " + credential + " does not match");
    }

}
