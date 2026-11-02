package org.example.bancodigitalbe.adapters.out.persistence;

import org.example.bancodigitalbe.domain.model.NewTransaction;
import org.example.bancodigitalbe.domain.model.Transaction;
import org.example.bancodigitalbe.domain.model.TransactionType;
import org.example.bancodigitalbe.domain.port.out.LoadDailyWithdrawnAmountPort;
import org.example.bancodigitalbe.domain.port.out.SaveTransactionPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
public class TransactionPersistenceAdapter implements SaveTransactionPort, LoadDailyWithdrawnAmountPort {

    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionPersistenceAdapter(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction save(NewTransaction newTransaction) {
        TransactionJpaEntity transactionJpaEntity = new TransactionJpaEntity();

        transactionJpaEntity.setAccountId(newTransaction.getAccountId());
        transactionJpaEntity.setType(newTransaction.getType());
        transactionJpaEntity.setAmount(newTransaction.getAmount());
        transactionJpaEntity.setBalanceAfter(newTransaction.getBalanceAfter());
        transactionJpaEntity.setCreatedAt(Instant.now());

        return transactionJpaEntityToDomainTransaction(transactionJpaRepository.save(transactionJpaEntity));
    }

    @Override
    public BigDecimal withdrawnBetween(Long accountId, Instant from, Instant to) {
        return transactionJpaRepository.sumAmountByTypeAndCreatedAtRange(
                accountId, TransactionType.WITHDRAWAL, from, to);
    }

    private Transaction transactionJpaEntityToDomainTransaction(TransactionJpaEntity transactionJpaEntity) {
        return new Transaction(
                transactionJpaEntity.getTransactionId(),
                transactionJpaEntity.getAccountId(),
                transactionJpaEntity.getType(),
                transactionJpaEntity.getAmount(),
                transactionJpaEntity.getBalanceAfter(),
                transactionJpaEntity.getCreatedAt()
        );
    }
}
