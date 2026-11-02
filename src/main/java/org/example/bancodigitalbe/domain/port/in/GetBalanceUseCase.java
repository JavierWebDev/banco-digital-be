package org.example.bancodigitalbe.domain.port.in;

import org.example.bancodigitalbe.domain.exceptions.AccountNotFoundException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.Account;

public interface GetBalanceUseCase {
    Account getBalance(String username) throws UserNotFoundException, AccountNotFoundException;
}
