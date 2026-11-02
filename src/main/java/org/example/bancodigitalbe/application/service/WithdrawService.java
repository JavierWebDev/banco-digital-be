package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.*;
import org.example.bancodigitalbe.domain.model.*;
import org.example.bancodigitalbe.domain.port.in.WithdrawCommand;
import org.example.bancodigitalbe.domain.port.in.WithdrawUseCase;
import org.example.bancodigitalbe.domain.port.out.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * RF06 to RF11, the critical module.
 *
 * RF11 asks for "la regla que no se cumple", in the singular, but says nothing
 * about which reason wins when several rules fail at once. The order below is
 * the one applied: security first (the PIN, so nothing about the account leaks
 * to someone who cannot prove who they are), then whether the request itself
 * makes sense, and only then the state of the account.
 */
@Service
public class WithdrawService implements WithdrawUseCase {

    private final LoadUserPort loadUserPort;
    private final LoadUserPinPort loadUserPinPort;
    private final LoadAccountPort loadAccountPort;
    private final LoadDailyWithdrawnAmountPort loadDailyWithdrawnAmountPort;
    private final UpdateAccountBalancePort updateAccountBalancePort;
    private final SaveTransactionPort saveTransactionPort;
    private final PasswordEncoder passwordEncoder;

    public WithdrawService(LoadUserPort loadUserPort, LoadUserPinPort loadUserPinPort,
                           LoadAccountPort loadAccountPort,
                           LoadDailyWithdrawnAmountPort loadDailyWithdrawnAmountPort,
                           UpdateAccountBalancePort updateAccountBalancePort,
                           SaveTransactionPort saveTransactionPort, PasswordEncoder passwordEncoder) {
        this.loadUserPort = loadUserPort;
        this.loadUserPinPort = loadUserPinPort;
        this.loadAccountPort = loadAccountPort;
        this.loadDailyWithdrawnAmountPort = loadDailyWithdrawnAmountPort;
        this.updateAccountBalancePort = updateAccountBalancePort;
        this.saveTransactionPort = saveTransactionPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public WithdrawalResult withdraw(WithdrawCommand command) {

        User user = loadUserPort.loadByUsername(command.getUsername())
                .orElseThrow(() -> new UserNotFoundException(command.getUsername()));

        // 1. RF07: PIN de exactamente 4 digitos y correcto (HU-05 escenario 2)
        SecurityPin.requireValidFormat(command.getPin());

        String encodedPin = loadUserPinPort.loadEncodedPin(user.getUserId())
                .orElseThrow(PinNotActivatedException::new);

        if (!passwordEncoder.matches(command.getPin(), encodedPin)) {
            throw new InvalidPinException();
        }

        // 2. RF09: monto minimo permitido (HU-05 escenario 3)
        if (command.getAmount().compareTo(WithdrawalPolicy.MINIMUM_AMOUNT) < 0) {
            throw new AmountBelowMinimumException(command.getAmount());
        }

        // 3. RF10: el monto tiene que poder entregarse con los billetes disponibles
        List<BillBundle> bills = CashDispenser.dispense(command.getAmount());

        Account account = loadAccountPort.loadByUserId(user.getUserId())
                .orElseThrow(() -> new AccountNotFoundException(user.getUserId()));

        // 4. RF06: saldo disponible, el motivo que RF11 omite de su lista
        //    pero que si aparece en HU-05 escenario 4
        if (account.getBalance().compareTo(command.getAmount()) < 0) {
            throw new InsufficientFundsException(command.getAmount(), account.getBalance());
        }

        // 5. RF08: tope acumulado de la jornada
        BigDecimal withdrawnToday = withdrawnToday(account.getAccountId());
        BigDecimal withdrawnAfter = withdrawnToday.add(command.getAmount());

        if (withdrawnAfter.compareTo(WithdrawalPolicy.DAILY_LIMIT) > 0) {
            throw new DailyWithdrawalLimitExceededException(withdrawnToday, command.getAmount());
        }

        // RF12: el saldo refleja la operacion
        BigDecimal newBalance = account.getBalance().subtract(command.getAmount());

        updateAccountBalancePort.updateBalance(account.getAccountId(), newBalance);

        Transaction transaction = saveTransactionPort.save(new NewTransaction(
                account.getAccountId(),
                TransactionType.WITHDRAWAL,
                command.getAmount(),
                newBalance
        ));

        return new WithdrawalResult(transaction, bills, withdrawnAfter);
    }

    private BigDecimal withdrawnToday(Long accountId) {
        LocalDate today = LocalDate.now(WithdrawalPolicy.ZONE);

        Instant from = today.atStartOfDay(WithdrawalPolicy.ZONE).toInstant();
        Instant to = today.plusDays(1).atStartOfDay(WithdrawalPolicy.ZONE).toInstant();

        return loadDailyWithdrawnAmountPort.withdrawnBetween(accountId, from, to);
    }

}
