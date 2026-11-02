package org.example.bancodigitalbe.adapters.out.persistence;

import org.example.bancodigitalbe.domain.model.Account;
import org.example.bancodigitalbe.domain.port.out.CreateAccountPort;
import org.example.bancodigitalbe.domain.port.out.LoadAccountPort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class AccountPersistenceAdapter implements CreateAccountPort, LoadAccountPort {

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.ZERO.setScale(2);

    private final AccountJpaRepository accountJpaRepository;

    public AccountPersistenceAdapter(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public Account createForUser(Long userId) {
        AccountJpaEntity accountJpaEntity = new AccountJpaEntity();

        accountJpaEntity.setUserId(userId);
        accountJpaEntity.setBalance(INITIAL_BALANCE);

        return accountJpaEntityToDomainAccount(accountJpaRepository.save(accountJpaEntity));
    }

    @Override
    public Optional<Account> loadByUserId(Long userId) {
        return accountJpaRepository.findByUserId(userId)
                .map(this::accountJpaEntityToDomainAccount);
    }

    private Account accountJpaEntityToDomainAccount(AccountJpaEntity accountJpaEntity) {
        return new Account(
                accountJpaEntity.getAccountId(),
                accountJpaEntity.getUserId(),
                accountJpaEntity.getBalance()
        );
    }
}
