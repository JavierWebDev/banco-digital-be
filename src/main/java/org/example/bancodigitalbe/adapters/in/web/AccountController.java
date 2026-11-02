package org.example.bancodigitalbe.adapters.in.web;

import org.example.bancodigitalbe.domain.model.Account;
import org.example.bancodigitalbe.domain.port.in.GetBalanceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final GetBalanceUseCase getBalanceUseCase;

    public AccountController(GetBalanceUseCase getBalanceUseCase) {
        this.getBalanceUseCase = getBalanceUseCase;
    }

    @GetMapping("/me/balance")
    public ResponseEntity<BalanceResponse> getBalance(Authentication authentication) {
        Account account = getBalanceUseCase.getBalance(authentication.getName());

        return ResponseEntity.ok(BalanceResponse.fromDomainAccount(account));
    }

}
