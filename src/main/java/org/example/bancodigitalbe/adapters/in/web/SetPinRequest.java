package org.example.bancodigitalbe.adapters.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * currentPin is only needed once a PIN has been activated; the first one is an
 * activation and has nothing to confirm.
 */
public record SetPinRequest(
        String currentPin,

        @NotBlank
        @Pattern(regexp = "^\\d{4}$", message = "el PIN debe componerse exactamente de 4 digitos")
        String newPin
) {}
