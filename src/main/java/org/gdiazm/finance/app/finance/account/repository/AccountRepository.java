package org.gdiazm.finance.app.finance.account.repository;

import org.gdiazm.finance.app.finance.account.entity.Account;
import org.gdiazm.finance.app.finance.account.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findByUserId(UUID userId);
    List<Account> findByUserIdAndIsActive(UUID userId, Boolean isActive);
    Optional<Account> findByIdAndUserId(UUID accountId, UUID userId);
}
