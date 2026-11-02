package org.example.bancodigitalbe.adapters.in.web;

import jakarta.validation.Valid;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.RegisterUserCommand;
import org.example.bancodigitalbe.domain.port.in.RegisterUserUseCase;
import org.example.bancodigitalbe.domain.port.in.UpdatePersonalDataCommand;
import org.example.bancodigitalbe.domain.port.in.UpdatePersonalDataUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

   RegisterUserUseCase registerUserUseCase;
   UpdatePersonalDataUseCase updatePersonalDataUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase, UpdatePersonalDataUseCase updatePersonalDataUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.updatePersonalDataUseCase = updatePersonalDataUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserSuccessResponse> register(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        RegisterUserCommand command = fromRequestToUserCommand(registerUserRequest);
        User user = registerUserUseCase.register(command);

        return ResponseEntity
                .created(URI.create("/users/" + user.getUserId()))
                .body(RegisterUserSuccessResponse.fromDomainUser(user));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserProfileResponse> updatePersonalData(Authentication authentication,
                                                                 @Valid @RequestBody UpdatePersonalDataRequest updatePersonalDataRequest) {
        User user = updatePersonalDataUseCase.updatePersonalData(new UpdatePersonalDataCommand(
                authentication.getName(),
                updatePersonalDataRequest.email(),
                updatePersonalDataRequest.phone(),
                updatePersonalDataRequest.address()
        ));

        return ResponseEntity.ok(UserProfileResponse.fromDomainUser(user));
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
