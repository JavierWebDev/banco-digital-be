package org.example.bancodigitalbe.domain.port.in;

import org.example.bancodigitalbe.domain.exceptions.AccountNotFoundException;
import org.example.bancodigitalbe.domain.exceptions.InvalidAmountException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.Transaction;

public interface DepositUseCase {
    Transaction deposit(DepositCommand command)
            throws UserNotFoundException, AccountNotFoundException, InvalidAmountException;
}
