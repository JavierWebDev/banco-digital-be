package org.example.bancodigitalbe.adapters.in.web;

import jakarta.validation.Valid;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.ChangePasswordCommand;
import org.example.bancodigitalbe.domain.port.in.ChangePasswordUseCase;
import org.example.bancodigitalbe.domain.port.in.RegisterUserCommand;
import org.example.bancodigitalbe.domain.port.in.SetPinCommand;
import org.example.bancodigitalbe.domain.port.in.SetPinUseCase;
import org.example.bancodigitalbe.domain.port.in.RegisterUserUseCase;
import org.example.bancodigitalbe.domain.port.in.UpdatePersonalDataCommand;
import org.example.bancodigitalbe.domain.port.in.UpdatePersonalDataUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

   RegisterUserUseCase registerUserUseCase;
   UpdatePersonalDataUseCase updatePersonalDataUseCase;
   ChangePasswordUseCase changePasswordUseCase;
   SetPinUseCase setPinUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase, UpdatePersonalDataUseCase updatePersonalDataUseCase,
                          ChangePasswordUseCase changePasswordUseCase, SetPinUseCase setPinUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.updatePersonalDataUseCase = updatePersonalDataUseCase;
        this.changePasswordUseCase = changePasswordUseCase;
        this.setPinUseCase = setPinUseCase;
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

    @PutMapping("/me/password")
    public ResponseEntity<MessageResponse> changePassword(Authentication authentication,
                                                          @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        changePasswordUseCase.changePassword(new ChangePasswordCommand(
                authentication.getName(),
                changePasswordRequest.currentPassword(),
                changePasswordRequest.newPassword()
        ));

        return ResponseEntity.ok(new MessageResponse("[+] clave de acceso actualizada correctamente"));
    }

    @PutMapping("/me/pin")
    public ResponseEntity<MessageResponse> setPin(Authentication authentication,
                                                  @Valid @RequestBody SetPinRequest setPinRequest) {
        setPinUseCase.setPin(new SetPinCommand(
                authentication.getName(),
                setPinRequest.currentPin(),
                setPinRequest.newPin()
        ));

        return ResponseEntity.ok(new MessageResponse("[+] PIN de seguridad actualizado correctamente"));
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
