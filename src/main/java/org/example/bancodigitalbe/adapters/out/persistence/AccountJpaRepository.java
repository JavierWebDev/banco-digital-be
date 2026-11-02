package org.example.bancodigitalbe.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountJpaRepository extends JpaRepository<AccountJpaEntity, Long> {
    Optional<AccountJpaEntity> findByUserId(Long userId);
}
