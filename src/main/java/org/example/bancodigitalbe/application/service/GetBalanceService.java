package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.AccountNotFoundException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.Account;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.GetBalanceUseCase;
import org.example.bancodigitalbe.domain.port.out.LoadAccountPort;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetBalanceService implements GetBalanceUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadAccountPort loadAccountPort;

    public GetBalanceService(LoadUserPort loadUserPort, LoadAccountPort loadAccountPort) {
        this.loadUserPort = loadUserPort;
        this.loadAccountPort = loadAccountPort;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getBalance(String username) throws UserNotFoundException, AccountNotFoundException {
        User user = loadUserPort.loadByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return loadAccountPort.loadByUserId(user.getUserId())
                .orElseThrow(() -> new AccountNotFoundException(user.getUserId()));
    }

}
