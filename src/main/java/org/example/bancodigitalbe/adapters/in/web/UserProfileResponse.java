package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.model.User;

import java.time.LocalDate;

public record UserProfileResponse(
        Long userId,
        String user,
        String name,
        String lastName,
        LocalDate birthDate,
        String email,
        String phone,
        String address
) {
    public static UserProfileResponse fromDomainUser(User user) {
        return new UserProfileResponse(
                user.getUserId(),
                user.getUser(),
                user.getName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress()
        );
    }
}
