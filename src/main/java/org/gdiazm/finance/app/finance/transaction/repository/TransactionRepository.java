package org.gdiazm.finance.app.finance.transaction.repository;

import org.gdiazm.finance.app.finance.common.entity.TransactionType;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUserIdOrderByCreatedAtDesc(UUID userId);
    Optional<Transaction> findByIdAndUserId(UUID id, UUID userId);
    boolean existsByCategoryId(UUID categoryId);
    boolean existsByAccountId(UUID accountId);

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.user.id = :userId
            AND (:type IS NULL OR t.type = :type)
            AND (:accountId IS NULL OR t.account.id = :accountId)
            AND (:startDate IS NULL OR t.createdAt >= :startDate)
            AND (:endDate IS NULL OR t.createdAt <= :endDate)
            ORDER BY t.createdAt DESC
            """)
    Page<Transaction> findByFilters(
            @Param("userId") UUID userId,
            @Param("type") TransactionType type,
            @Param("accountId") UUID accountId,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate,
            Pageable pageable
    );
}
