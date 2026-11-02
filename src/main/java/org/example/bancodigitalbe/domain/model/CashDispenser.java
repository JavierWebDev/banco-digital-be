package org.example.bancodigitalbe.domain.model;

import org.example.bancodigitalbe.domain.exceptions.AmountNotDispensableException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * RF10: works out the delivery with the fewest possible bills.
 *
 * With an unlimited supply of each denomination and $10.000 as the smallest
 * bill, the greedy pass is always optimal, and the only amount that cannot be
 * delivered is one that is not a multiple of $10.000. HU-05 escenario 1 calls
 * this a "multiplo exacto de las denominaciones", which is not the same rule:
 * $30.000 is payable as $20.000 + $10.000 even though it is a multiple of
 * neither $20.000 nor $100.000.
 */
public final class CashDispenser {

    private CashDispenser() {
    }

    public static List<BillBundle> dispense(BigDecimal amount) throws AmountNotDispensableException {
        List<BillBundle> bundles = new ArrayList<>();
        BigDecimal remaining = amount;

        for (Integer denomination : WithdrawalPolicy.DENOMINATIONS) {
            BigDecimal bill = BigDecimal.valueOf(denomination);
            int quantity = remaining.divideToIntegralValue(bill).intValue();

            if (quantity > 0) {
                bundles.add(new BillBundle(denomination, quantity));
                remaining = remaining.subtract(bill.multiply(BigDecimal.valueOf(quantity)));
            }
        }

        if (remaining.signum() != 0) {
            throw new AmountNotDispensableException(amount);
        }

        return List.copyOf(bundles);
    }

}
