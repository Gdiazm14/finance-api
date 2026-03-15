package org.gdiazm.finance.app.finance.transaction.repository;

import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
