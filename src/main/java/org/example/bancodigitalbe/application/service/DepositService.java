package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.AccountNotFoundException;
import org.example.bancodigitalbe.domain.exceptions.InvalidAmountException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.Account;
import org.example.bancodigitalbe.domain.model.NewTransaction;
import org.example.bancodigitalbe.domain.model.Transaction;
import org.example.bancodigitalbe.domain.model.TransactionType;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.DepositCommand;
import org.example.bancodigitalbe.domain.port.in.DepositUseCase;
import org.example.bancodigitalbe.domain.port.out.LoadAccountPort;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.example.bancodigitalbe.domain.port.out.SaveTransactionPort;
import org.example.bancodigitalbe.domain.port.out.UpdateAccountBalancePort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DepositService implements DepositUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountBalancePort updateAccountBalancePort;
    private final SaveTransactionPort saveTransactionPort;

    public DepositService(LoadUserPort loadUserPort, LoadAccountPort loadAccountPort,
                          UpdateAccountBalancePort updateAccountBalancePort, SaveTransactionPort saveTransactionPort) {
        this.loadUserPort = loadUserPort;
        this.loadAccountPort = loadAccountPort;
        this.updateAccountBalancePort = updateAccountBalancePort;
        this.saveTransactionPort = saveTransactionPort;
    }

    @Override
    @Transactional
    public Transaction deposit(DepositCommand command)
            throws UserNotFoundException, AccountNotFoundException, InvalidAmountException {

        // HU-02 escenario 2: $0, negativo o vacio deja el saldo intacto
        if (command.getAmount().signum() <= 0) {
            throw new InvalidAmountException(command.getAmount());
        }

        User user = loadUserPort.loadByUsername(command.getUsername())
                .orElseThrow(() -> new UserNotFoundException(command.getUsername()));

        Account account = loadAccountPort.loadByUserId(user.getUserId())
                .orElseThrow(() -> new AccountNotFoundException(user.getUserId()));

        BigDecimal newBalance = account.getBalance().add(command.getAmount());

        updateAccountBalancePort.updateBalance(account.getAccountId(), newBalance);

        return saveTransactionPort.save(new NewTransaction(
                account.getAccountId(),
                TransactionType.DEPOSIT,
                command.getAmount(),
                newBalance
        ));
    }

}
