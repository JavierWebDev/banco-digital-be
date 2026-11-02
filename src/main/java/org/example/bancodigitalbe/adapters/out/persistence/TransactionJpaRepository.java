package org.example.bancodigitalbe.adapters.out.persistence;

import org.example.bancodigitalbe.domain.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaEntity, Long> {

    @Query("""
            select coalesce(sum(t.amount), 0)
            from TransactionJpaEntity t
            where t.accountId = :accountId
              and t.type = :type
              and t.createdAt >= :from
              and t.createdAt < :to
            """)
    BigDecimal sumAmountByTypeAndCreatedAtRange(@Param("accountId") Long accountId,
                                                @Param("type") TransactionType type,
                                                @Param("from") Instant from,
                                                @Param("to") Instant to);
}
