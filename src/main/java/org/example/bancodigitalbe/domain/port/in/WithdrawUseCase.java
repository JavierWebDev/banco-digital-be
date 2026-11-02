package org.example.bancodigitalbe.domain.port.in;

import org.example.bancodigitalbe.domain.model.WithdrawalResult;

public interface WithdrawUseCase {
    WithdrawalResult withdraw(WithdrawCommand command);
}
