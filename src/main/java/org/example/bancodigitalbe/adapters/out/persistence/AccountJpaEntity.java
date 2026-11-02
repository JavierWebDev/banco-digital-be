package org.example.bancodigitalbe.adapters.out.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "account")
public class AccountJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotNull
    @Column(unique = true)
    private Long userId;

    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal balance;

    public AccountJpaEntity() {
    }
}
