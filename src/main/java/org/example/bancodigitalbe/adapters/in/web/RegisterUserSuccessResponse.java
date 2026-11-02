package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.model.User;

public record RegisterUserSuccessResponse(
        Long userId,
        String user
) {
    public static RegisterUserSuccessResponse fromDomainUser(User user) {
        return new RegisterUserSuccessResponse(user.getUserId(), user.getUser());
    }
}
