package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.AccountNotFoundException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.Account;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.out.LoadAccountPort;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBalanceServiceTest {

    @Mock
    private LoadUserPort loadUserPort;

    @Mock
    private LoadAccountPort loadAccountPort;

    private User existingUser() {
        return new User(1L, "javier", "$2a$10$hash", "Javier", "Gomez", LocalDate.of(2000, 1, 1), null, null, null);
    }

    // HU-01 escenario 1: visualizacion exitosa del balance actual
    @Test
    void returnsTheCurrentBalanceOfTheAuthenticatedUser() {
        GetBalanceService getBalanceService = new GetBalanceService(loadUserPort, loadAccountPort);
        when(loadUserPort.loadByUsername("javier")).thenReturn(Optional.of(existingUser()));
        when(loadAccountPort.loadByUserId(1L))
                .thenReturn(Optional.of(new Account(7L, 1L, new BigDecimal("250000.00"))));

        Account account = getBalanceService.getBalance("javier");

        assertThat(account.getBalance()).isEqualByComparingTo("250000.00");
        assertThat(account.getAccountId()).isEqualTo(7L);
    }

    // HU-01 escenario 2: saldo en cero, sin generar excepciones
    @Test
    void returnsZeroWithoutFailingWhenTheAccountIsEmpty() {
        GetBalanceService getBalanceService = new GetBalanceService(loadUserPort, loadAccountPort);
        when(loadUserPort.loadByUsername("javier")).thenReturn(Optional.of(existingUser()));
        when(loadAccountPort.loadByUserId(1L))
                .thenReturn(Optional.of(new Account(7L, 1L, BigDecimal.ZERO.setScale(2))));

        Account account = getBalanceService.getBalance("javier");

        assertThat(account.getBalance()).isEqualByComparingTo("0");
    }

    @Test
    void failsWhenTheAuthenticatedUsernameNoLongerExists() {
        GetBalanceService getBalanceService = new GetBalanceService(loadUserPort, loadAccountPort);
        when(loadUserPort.loadByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getBalanceService.getBalance("ghost"))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    void failsWhenTheUserHasNoAccount() {
        GetBalanceService getBalanceService = new GetBalanceService(loadUserPort, loadAccountPort);
        when(loadUserPort.loadByUsername("javier")).thenReturn(Optional.of(existingUser()));
        when(loadAccountPort.loadByUserId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> getBalanceService.getBalance("javier"))
                .isInstanceOf(AccountNotFoundException.class);
    }

}
