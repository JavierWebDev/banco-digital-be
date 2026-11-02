package org.example.bancodigitalbe.adapters.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegisterUserRequest(
        @NotBlank @Size(max = 40) String user,
        @NotBlank @Size(min = 8, max = 72) String password,
        @NotBlank @Size(max = 50) String name,
        @NotBlank @Size(max = 50) String lastName,
        @NotNull @Past LocalDate birthDate
) {}