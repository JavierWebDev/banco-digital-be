package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.LoginUseCase;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;

import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginUseCase {

    private final LoadUserPort loadUserPort;

    public LoginService(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public User login(String username) throws UserNotFoundException {
        return loadUserPort.loadByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

}
