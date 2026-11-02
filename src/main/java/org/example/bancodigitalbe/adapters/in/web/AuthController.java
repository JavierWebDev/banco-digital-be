package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.LoginUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<UserProfileResponse> login(Authentication authentication) {
        User user = loginUseCase.login(authentication.getName());

        return ResponseEntity.ok(UserProfileResponse.fromDomainUser(user));
    }

}
