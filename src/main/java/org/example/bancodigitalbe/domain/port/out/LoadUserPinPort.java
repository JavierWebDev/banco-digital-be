package org.example.bancodigitalbe.domain.port.out;

import java.util.Optional;

/**
 * The PIN hash is loaded on its own, and never as part of the User model, so it
 * only ever reaches the two use cases that actually need it.
 */
public interface LoadUserPinPort {
    Optional<String> loadEncodedPin(Long userId);
}
