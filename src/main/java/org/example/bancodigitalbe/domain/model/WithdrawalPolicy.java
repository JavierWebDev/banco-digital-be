package org.example.bancodigitalbe.domain.model;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

/**
 * The operating rules of the ATM module (RF08, RF09, RF10).
 */
public final class WithdrawalPolicy {

    public static final BigDecimal MINIMUM_AMOUNT = new BigDecimal("10000");

    public static final BigDecimal DAILY_LIMIT = new BigDecimal("1000000");

    /** Physical denominations, highest first, so the greedy dispenser hands out the fewest bills. */
    public static final List<Integer> DENOMINATIONS = List.of(100_000, 50_000, 20_000, 10_000);

    /**
     * Neither the requirements nor the user stories say which calendar day the
     * "dia natural" / "jornada" of RF08 belongs to, so the daily cap is counted
     * in the bank's own time zone.
     */
    public static final ZoneId ZONE = ZoneId.of("America/Bogota");

    private WithdrawalPolicy() {
    }

}
