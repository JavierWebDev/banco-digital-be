package org.example.bancodigitalbe.domain.model;

import org.example.bancodigitalbe.domain.exceptions.InvalidPinFormatException;

import java.util.regex.Pattern;

/**
 * The transactional PIN required by RF07: exactly four digits, no more and no
 * less. Keeping the rule here means every module that touches a PIN enforces
 * the very same definition.
 */
public final class SecurityPin {

    public static final int LENGTH = 4;

    private static final Pattern FOUR_DIGITS = Pattern.compile("^\\d{" + LENGTH + "}$");

    private SecurityPin() {
    }

    public static void requireValidFormat(String pin) throws InvalidPinFormatException {
        if (pin == null || !FOUR_DIGITS.matcher(pin).matches()) {
            throw new InvalidPinFormatException();
        }
    }

}
