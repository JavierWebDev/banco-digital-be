package org.example.bancodigitalbe.adapters.out.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "user")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Column(unique = true)
    @Size(max = 40)
    private String user;

    @NotBlank
    @Size(max = 60)
    private String password;

    // BCrypt hash of the 4 digit transactional PIN (RF07); null until activated
    @Size(max = 60)
    private String pin;

    // CuentaBancaria cuentaBancaria;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    // contact data is filled in later, through the personal data module (RF04)
    @Size(max = 120)
    private String email;

    @Size(max = 10)
    private String phone;

    @Size(max = 150)
    private String address;

    public UserJpaEntity() {
    }
}
