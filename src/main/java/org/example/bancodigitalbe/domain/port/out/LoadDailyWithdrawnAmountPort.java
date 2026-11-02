package org.example.bancodigitalbe.domain.port.out;

import java.math.BigDecimal;
import java.time.Instant;

public interface LoadDailyWithdrawnAmountPort {
    BigDecimal withdrawnBetween(Long accountId, Instant from, Instant to);
}
