package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.exceptions.AccountNotFoundException;
import org.example.bancodigitalbe.domain.exceptions.InvalidAmountException;
import org.example.bancodigitalbe.domain.exceptions.InvalidCurrentCredentialException;
import org.example.bancodigitalbe.domain.exceptions.InvalidPinFormatException;
import org.example.bancodigitalbe.domain.exceptions.InvalidPersonalDataException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.exceptions.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameAlreadyExists(UsernameAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiErrorResponse.of("USERNAME_ALREADY_EXISTS", e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.of("USER_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleAccountNotFound(AccountNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiErrorResponse.of("ACCOUNT_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidAmount(InvalidAmountException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of("INVALID_AMOUNT", e.getMessage()));
    }

    @ExceptionHandler(InvalidPersonalDataException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPersonalData(InvalidPersonalDataException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of("INVALID_PERSONAL_DATA", e.getMessage()));
    }

    @ExceptionHandler(InvalidCurrentCredentialException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidCurrentCredential(InvalidCurrentCredentialException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of("INVALID_CURRENT_CREDENTIAL", e.getMessage()));
    }

    @ExceptionHandler(InvalidPinFormatException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPinFormat(InvalidPinFormatException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiErrorResponse.of("INVALID_PIN_FORMAT", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .badRequest()
                .body(new ApiErrorResponse("VALIDATION_ERROR", "[!] invalid request payload", fieldErrors));
    }

}
