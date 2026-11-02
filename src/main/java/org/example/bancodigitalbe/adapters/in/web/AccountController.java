package org.example.bancodigitalbe.adapters.in.web;

import jakarta.validation.Valid;
import org.example.bancodigitalbe.domain.model.Account;
import org.example.bancodigitalbe.domain.model.Transaction;
import org.example.bancodigitalbe.domain.port.in.DepositCommand;
import org.example.bancodigitalbe.domain.port.in.DepositUseCase;
import org.example.bancodigitalbe.domain.port.in.GetBalanceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final GetBalanceUseCase getBalanceUseCase;
    private final DepositUseCase depositUseCase;

    public AccountController(GetBalanceUseCase getBalanceUseCase, DepositUseCase depositUseCase) {
        this.getBalanceUseCase = getBalanceUseCase;
        this.depositUseCase = depositUseCase;
    }

    @GetMapping("/me/balance")
    public ResponseEntity<BalanceResponse> getBalance(Authentication authentication) {
        Account account = getBalanceUseCase.getBalance(authentication.getName());

        return ResponseEntity.ok(BalanceResponse.fromDomainAccount(account));
    }

    @PostMapping("/me/deposits")
    public ResponseEntity<TransactionResponse> deposit(Authentication authentication,
                                                      @Valid @RequestBody DepositRequest depositRequest) {
        Transaction transaction = depositUseCase.deposit(
                new DepositCommand(authentication.getName(), depositRequest.amount()));

        return ResponseEntity.ok(TransactionResponse.fromDomainTransaction(
                transaction, "[+] deposito procesado correctamente"));
    }

}
