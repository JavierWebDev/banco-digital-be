package org.example.bancodigitalbe.domain.exceptions;

import lombok.Getter;
import org.example.bancodigitalbe.domain.model.WithdrawalPolicy;

import java.math.BigDecimal;

@Getter
public class DailyWithdrawalLimitExceededException extends RuntimeException {
    private final BigDecimal withdrawnToday;
    private final BigDecimal amount;

    public DailyWithdrawalLimitExceededException(BigDecimal withdrawnToday, BigDecimal amount) {
        super("[!] el retiro de {" + amount + "} llevaria el acumulado del dia a {"
                + withdrawnToday.add(amount) + "}, que supera el tope diario de $"
                + WithdrawalPolicy.DAILY_LIMIT + " COP");
        this.withdrawnToday = withdrawnToday;
        this.amount = amount;
    }

}
