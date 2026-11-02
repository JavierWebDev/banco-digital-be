package org.example.bancodigitalbe.domain.port.out;

import org.example.bancodigitalbe.domain.model.User;

public interface UpdateUserPersonalDataPort {
    User updatePersonalData(Long userId, String email, String phone, String address);
}
