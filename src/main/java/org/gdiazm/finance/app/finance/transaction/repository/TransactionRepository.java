package org.gdiazm.finance.app.finance.transaction.repository;

import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUserIdOrderByCreatedAtDesc(UUID userId);
    Optional<Transaction> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByCategoryId(UUID categoryId);
    boolean existsByAccountId(UUID accountId);
}
