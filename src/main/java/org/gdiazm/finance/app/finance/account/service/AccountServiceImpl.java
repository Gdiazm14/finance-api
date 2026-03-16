package org.gdiazm.finance.app.finance.account.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.account.dto.AccountRequest;
import org.gdiazm.finance.app.finance.account.dto.AccountResponse;
import org.gdiazm.finance.app.finance.account.dto.AccountUpdateRequest;
import org.gdiazm.finance.app.finance.account.entity.Account;
import org.gdiazm.finance.app.finance.account.entity.AccountType;
import org.gdiazm.finance.app.finance.account.mapper.AccountMapper;
import org.gdiazm.finance.app.finance.account.repository.AccountRepository;
import org.gdiazm.finance.app.finance.common.exception.BusinessException;
import org.gdiazm.finance.app.finance.common.exception.ResourceNotFoundException;
import org.gdiazm.finance.app.finance.security.SecurityUtils;
import org.gdiazm.finance.app.finance.user.entity.User;
import org.gdiazm.finance.app.finance.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public AccountResponse createAccount(AccountRequest request) {
        User user = userRepository.getReferenceById(SecurityUtils.getCurrentUserId());

        Account account = accountMapper.toEntity(request);
        account.setUser(user);

        if(Boolean.TRUE.equals(account.getAllowNegativeBalance())
                && request.getAccountType() != AccountType.CREDIT_CARD) {
            throw new BusinessException("Only credit card accounts allow negative balance");
        }
        accountRepository.saveAndFlush(account);
        entityManager.refresh(account);
         return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    public List<AccountResponse> getAccounts(Boolean active) {
        UUID userId = SecurityUtils.getCurrentUserId();
        List<Account> accounts = (active !=null)
                ? accountRepository.findByUserIdAndIsActive(userId, active)
                :accountRepository.findByUserId(userId);
        return accounts.stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    @Override
    public AccountResponse getAccountById(UUID accountId) {
        return accountMapper.toResponse(findAccountForCurrentUser(accountId));
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(UUID accountId, AccountUpdateRequest request) {
        Account account = findAccountForCurrentUser(accountId);
        accountMapper.updateEntityFromRequest(request, account);
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public void deleteAccountById(UUID accountId) {
    Account account = findAccountForCurrentUser(accountId);
    account.setIsActive(false);
    accountRepository.save(account);
    }

    private Account findAccountForCurrentUser(UUID accountId) {
        return accountRepository.findByIdAndUserId(
                accountId,
                SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }
}
