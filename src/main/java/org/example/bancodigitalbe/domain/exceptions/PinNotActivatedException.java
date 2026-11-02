package org.example.bancodigitalbe.domain.exceptions;

public class PinNotActivatedException extends RuntimeException {

    public PinNotActivatedException() {
        super("[!] no hay un PIN de seguridad activo; debe activarlo antes de retirar");
    }

}
