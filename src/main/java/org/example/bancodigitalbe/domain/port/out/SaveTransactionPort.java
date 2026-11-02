package org.example.bancodigitalbe.domain.port.out;

import org.example.bancodigitalbe.domain.model.NewTransaction;
import org.example.bancodigitalbe.domain.model.Transaction;

public interface SaveTransactionPort {
    Transaction save(NewTransaction newTransaction);
}
