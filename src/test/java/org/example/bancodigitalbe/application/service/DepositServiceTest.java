package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.InvalidAmountException;
import org.example.bancodigitalbe.domain.model.Account;
import org.example.bancodigitalbe.domain.model.NewTransaction;
import org.example.bancodigitalbe.domain.model.Transaction;
import org.example.bancodigitalbe.domain.model.TransactionType;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.DepositCommand;
import org.example.bancodigitalbe.domain.port.out.LoadAccountPort;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.example.bancodigitalbe.domain.port.out.SaveTransactionPort;
import org.example.bancodigitalbe.domain.port.out.UpdateAccountBalancePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    private LoadUserPort loadUserPort;

    @Mock
    private LoadAccountPort loadAccountPort;

    @Mock
    private UpdateAccountBalancePort updateAccountBalancePort;

    @Mock
    private SaveTransactionPort saveTransactionPort;

    private DepositService depositService;

    @BeforeEach
    void setUp() {
        depositService = new DepositService(loadUserPort, loadAccountPort, updateAccountBalancePort, saveTransactionPort);
    }

    private void givenAccountWithBalance(String balance) {
        when(loadUserPort.loadByUsername("javier")).thenReturn(Optional.of(
                new User(1L, "javier", "$2a$10$hash", "Javier", "Gomez", LocalDate.of(2000, 1, 1))));
        when(loadAccountPort.loadByUserId(1L)).thenReturn(Optional.of(
                new Account(7L, 1L, new BigDecimal(balance))));
    }

    // HU-02 escenario 1: deposito exitoso, incrementa el saldo y emite comprobante
    @Test
    void increasesTheBalanceAndRecordsTheTransaction() {
        givenAccountWithBalance("100000.00");
        when(saveTransactionPort.save(any())).thenAnswer(invocation -> {
            NewTransaction newTransaction = invocation.getArgument(0);
            return new Transaction(99L, newTransaction.getAccountId(), newTransaction.getType(),
                    newTransaction.getAmount(), newTransaction.getBalanceAfter(), Instant.now());
        });

        Transaction transaction = depositService.deposit(new DepositCommand("javier", new BigDecimal("50000")));

        verify(updateAccountBalancePort).updateBalance(7L, new BigDecimal("150000.00"));
        assertThat(transaction.getType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(transaction.getBalanceAfter()).isEqualByComparingTo("150000.00");

        ArgumentCaptor<NewTransaction> captor = ArgumentCaptor.forClass(NewTransaction.class);
        verify(saveTransactionPort).save(captor.capture());
        assertThat(captor.getValue().getAmount()).isEqualByComparingTo("50000");
    }

    // HU-02 escenario 2: monto en cero mantiene intacto el saldo
    @Test
    void rejectsAZeroAmountAndLeavesTheBalanceUntouched() {
        assertThatThrownBy(() -> depositService.deposit(new DepositCommand("javier", BigDecimal.ZERO)))
                .isInstanceOf(InvalidAmountException.class);

        verify(updateAccountBalancePort, never()).updateBalance(any(), any());
        verify(saveTransactionPort, never()).save(any());
    }

    // HU-02 escenario 2: monto negativo mantiene intacto el saldo
    @Test
    void rejectsANegativeAmountAndLeavesTheBalanceUntouched() {
        assertThatThrownBy(() -> depositService.deposit(new DepositCommand("javier", new BigDecimal("-50000"))))
                .isInstanceOf(InvalidAmountException.class);

        verify(updateAccountBalancePort, never()).updateBalance(any(), any());
        verify(saveTransactionPort, never()).save(any());
    }

}
