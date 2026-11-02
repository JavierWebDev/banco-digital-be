package org.example.bancodigitalbe.adapters.out.persistence;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.bancodigitalbe.domain.model.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "transaction", indexes = {
        @Index(name = "idx_transaction_account_created", columnList = "accountId, createdAt")
})
public class TransactionJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @NotNull
    private Long accountId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TransactionType type;

    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(precision = 19, scale = 2)
    private BigDecimal balanceAfter;

    @NotNull
    private Instant createdAt;

    public TransactionJpaEntity() {
    }
}
