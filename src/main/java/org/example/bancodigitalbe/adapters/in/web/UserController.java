package org.example.bancodigitalbe.adapters.in.web;

import jakarta.validation.Valid;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.RegisterUserCommand;
import org.example.bancodigitalbe.domain.port.in.RegisterUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

   RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserSuccessResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        RegisterUserCommand command = fromRequestToUserCommand(registerUserRequest);
        User user = registerUserUseCase.register(command);

        return ResponseEntity
                .created(URI.create("/users/" + user.getUserId()))
                .body(RegisterUserSuccessResponse.fromDomainUser(user));
    }

    private RegisterUserCommand fromRequestToUserCommand(RegisterUserRequest registerUserRequest) {
        return new RegisterUserCommand(
                registerUserRequest.user(),
                registerUserRequest.password(),
                registerUserRequest.name(),
                registerUserRequest.lastName(),
                registerUserRequest.birthDate()
        );
    }

}
