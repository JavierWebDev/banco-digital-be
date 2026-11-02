package org.example.bancodigitalbe.adapters.in.web;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Every field is optional: a null field is left untouched. The patterns reject
 * blank strings on purpose, so an empty field is an error and not an erasure
 * (HU-03 escenario 2).
 */
public record UpdatePersonalDataRequest(
        @Size(max = 120)
        @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[A-Za-z]{2,}$", message = "debe tener un formato de correo valido")
        String email,

        @Pattern(regexp = "^\\d{10}$", message = "debe tener exactamente 10 digitos")
        String phone,

        @Size(min = 1, max = 150, message = "no puede estar vacia")
        String address
) {}
