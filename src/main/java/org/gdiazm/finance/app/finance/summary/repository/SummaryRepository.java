package org.gdiazm.finance.app.finance.summary.repository;

import org.gdiazm.finance.app.finance.summary.dto.CategorySpentProjection;
import org.gdiazm.finance.app.finance.summary.dto.CategorySummaryResponse;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface SummaryRepository extends JpaRepository<Transaction, UUID> {

    @Query("""
                        SELECT COALESCE(SUM(t.amount), 0)
                        FROM Transaction t
                        WHERE t.user.id = :userId
                        AND t.type = 'INCOME'
                        AND t.createdAt >= :start
                        AND t.createdAt < :end
            """)
    BigDecimal getTotalIncome(@Param("userId") UUID userId,
                              @Param("start") OffsetDateTime start,
                              @Param("end") OffsetDateTime end);


    @Query("""
                SELECT COALESCE(SUM(t.amount), 0)
                FROM Transaction t
                WHERE t.user.id = :userId
                AND t.type = 'EXPENSE'
                AND t.createdAt >= :start
                AND t.createdAt < :end
            
            """)
    BigDecimal getTotalExpense(@Param("userId") UUID userId,
                                @Param("start") OffsetDateTime start,
                                @Param("end") OffsetDateTime end);


    @Query("""
            SELECT t.category.id AS categoryId,
                        COALESCE(SUM(t.amount),0) AS spent
            FROM Transaction t
            WHERE t.user.id = :userId
            AND t.type = 'EXPENSE'
            AND t.category IS NOT NULL
            AND t.createdAt >= :start
            AND t.createdAt < :end
            GROUP BY t.category.id
            """)
    List<CategorySpentProjection> getSpentByCategory(
            @Param("userId") UUID userId,
            @Param("start") OffsetDateTime start,
            @Param("end") OffsetDateTime end
    );

}
