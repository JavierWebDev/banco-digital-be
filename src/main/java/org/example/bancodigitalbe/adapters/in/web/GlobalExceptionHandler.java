package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.exceptions.AccountNotFoundException;
import org.example.bancodigitalbe.domain.exceptions.AmountBelowMinimumException;
import org.example.bancodigitalbe.domain.exceptions.AmountNotDispensableException;
import org.example.bancodigitalbe.domain.exceptions.DailyWithdrawalLimitExceededException;
import org.example.bancodigitalbe.domain.exceptions.InsufficientFundsException;
import org.example.bancodigitalbe.domain.exceptions.InvalidPinException;
import org.example.bancodigitalbe.domain.exceptions.PinNotActivatedException;
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

    // RF11: cada rechazo de retiro indica exactamente la regla que no se cumple

    @ExceptionHandler(InvalidPinException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidPin(InvalidPinException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiErrorResponse.of("INVALID_PIN", e.getMessage()));
    }

    @ExceptionHandler(PinNotActivatedException.class)
    public ResponseEntity<ApiErrorResponse> handlePinNotActivated(PinNotActivatedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiErrorResponse.of("PIN_NOT_ACTIVATED", e.getMessage()));
    }

    @ExceptionHandler(AmountBelowMinimumException.class)
    public ResponseEntity<ApiErrorResponse> handleAmountBelowMinimum(AmountBelowMinimumException e) {
        return ResponseEntity
                .unprocessableEntity()
                .body(ApiErrorResponse.of("AMOUNT_BELOW_MINIMUM", e.getMessage()));
    }

    @ExceptionHandler(AmountNotDispensableException.class)
    public ResponseEntity<ApiErrorResponse> handleAmountNotDispensable(AmountNotDispensableException e) {
        return ResponseEntity
                .unprocessableEntity()
                .body(ApiErrorResponse.of("AMOUNT_NOT_DISPENSABLE", e.getMessage()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ApiErrorResponse> handleInsufficientFunds(InsufficientFundsException e) {
        return ResponseEntity
                .unprocessableEntity()
                .body(ApiErrorResponse.of("INSUFFICIENT_FUNDS", e.getMessage()));
    }

    @ExceptionHandler(DailyWithdrawalLimitExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleDailyLimitExceeded(DailyWithdrawalLimitExceededException e) {
        return ResponseEntity
                .unprocessableEntity()
                .body(ApiErrorResponse.of("DAILY_LIMIT_EXCEEDED", e.getMessage()));
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
