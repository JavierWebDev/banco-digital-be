package org.example.bancodigitalbe.domain.port.out;

public interface UpdateUserCredentialsPort {
    void updatePassword(Long userId, String encodedPassword);

    void updatePin(Long userId, String encodedPin);
}
