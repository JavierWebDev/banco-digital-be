package org.example.bancodigitalbe.adapters.in.web;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawRequest(
        @NotNull @Positive @Digits(integer = 17, fraction = 2) BigDecimal amount,

        @NotBlank
        @Pattern(regexp = "^\\d{4}$", message = "el PIN debe componerse exactamente de 4 digitos")
        String pin
) {}
