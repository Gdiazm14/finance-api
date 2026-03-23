package org.gdiazm.finance.app.finance.transaction.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.account.entity.Account;
import org.gdiazm.finance.app.finance.account.entity.AccountType;
import org.gdiazm.finance.app.finance.account.repository.AccountRepository;
import org.gdiazm.finance.app.finance.category.entity.Category;
import org.gdiazm.finance.app.finance.category.repository.CategoryRepository;
import org.gdiazm.finance.app.finance.common.dto.PageResponse;
import org.gdiazm.finance.app.finance.common.entity.TransactionType;
import org.gdiazm.finance.app.finance.common.exception.BusinessException;
import org.gdiazm.finance.app.finance.common.exception.ResourceNotFoundException;
import org.gdiazm.finance.app.finance.security.SecurityUtils;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.gdiazm.finance.app.finance.transaction.mapper.TransactionMapper;
import org.gdiazm.finance.app.finance.transaction.repository.TransactionRepository;
import org.gdiazm.finance.app.finance.user.entity.User;
import org.gdiazm.finance.app.finance.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;
    private final EntityManager entityManager;


    @Override
    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.getReferenceById(userId);
        Account account = findAccountForUser(request.getAccountId(), userId);

        if(!Boolean.TRUE.equals(account.getIsActive())) {
            throw new BusinessException("Account is inactive and cannot receive new transactions");
        }

        BigDecimal amount = request.getAmount();
        Transaction transaction = transactionMapper.toEntity(request);
        transaction.setAccount(account);
        transaction.setUser(user);


        switch (request.getType()) {
            case EXPENSE -> processExpense(transaction, account, amount, request.getCategoryId(), userId);
            case INCOME -> processIncome(transaction, account, amount, request.getCategoryId(), userId);
            case TRANSFER -> processTransfer(transaction, account, amount, request.getDestinationAccountId(), userId);
        }

        transactionRepository.saveAndFlush(transaction);
        entityManager.refresh(transaction);

        return transactionMapper.toResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TransactionResponse> getTransactions(
            TransactionType type,
            UUID accountId,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionResponse> result = transactionRepository.findByFilters(
                SecurityUtils.getCurrentUserId(),
                type,
                accountId,
                startDate,
                endDate,
                pageable
        ).map(transactionMapper::toResponse);
        return PageResponse.of(result);
    }

//    @Override
//    public List<TransactionResponse> getTransactions() {
//        return transactionRepository.findByUserIdOrderByCreatedAtDesc(SecurityUtils.getCurrentUserId())
//                .stream()
//                .map(transactionMapper::toResponse)
//                .toList();
//    }

    @Override
    public TransactionResponse getTransactionById(UUID transactionId) {
        return transactionMapper.toResponse(
                transactionRepository.findByIdAndUserId(transactionId, SecurityUtils.getCurrentUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("Transaction with id: " + transactionId + " not found"))
        );
    }


    //--Handlers por tipo

//    private void processExpense(Transaction transaction, Account account,
//                                BigDecimal amount, UUID categoryId, UUID userId) {
//
//        if (categoryId == null) {
//            throw new BusinessException("Category is required for Expense Transaction");
//        }
//        Category category = findCategoryForUser(categoryId, userId);
//
//        //Validar fondos en la cuenta - salvo que permita negativos
//
//        if (!Boolean.TRUE.equals(account.getAllowNegativeBalance())
//                && account.getBalance().compareTo(amount) < 0) {
//            throw new BusinessException("Insufficient account balance");
//        }
//
//        if (category.getBudgetAmount().compareTo(amount) < 0) {
//            throw new BusinessException("Insufficient category amount");
//        }
//        account.setBalance(account.getBalance().subtract(amount));
//        //category.setBudgetAmount(category.getBudgetAmount().subtract(amount));
//        transaction.setCategory(category);
//    }

    private void processExpense(Transaction transaction, Account account,
                                BigDecimal amount, UUID categoryId, UUID userId) {
        if (categoryId == null) {
            throw new BusinessException("Category is required for EXPENSE transactions");
        }

        Category category = findCategoryForUser(categoryId, userId);

        // Validar fondos en cuenta
        if (!Boolean.TRUE.equals(account.getAllowNegativeBalance())
                && account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Insufficient account balance");
        }

        // Validar presupuesto mensual de la categoría
        if (category.getBudgetAmount().compareTo(BigDecimal.ZERO) > 0) {
            YearMonth now = YearMonth.now();
            OffsetDateTime start = now.atDay(1).atStartOfDay().atOffset(ZoneOffset.UTC);
            OffsetDateTime end = now.atEndOfMonth().atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

            BigDecimal spentThisMonth = transactionRepository
                    .getSpentByCategoryAndPeriod(categoryId, start, end);

            if (spentThisMonth.add(amount).compareTo(category.getBudgetAmount()) > 0) {
                throw new BusinessException("Insufficient category budget for this month");
            }
        }

        account.setBalance(account.getBalance().subtract(amount));
        transaction.setCategory(category);
    }



    private void processIncome(Transaction transaction, Account account,
                               BigDecimal amount, UUID categoryId, UUID userId) {

        account.setBalance(account.getBalance().add(amount));
        if (categoryId != null) {
            Category category = findCategoryForUser(categoryId, userId);
            transaction.setCategory(category);
        }
    }

    private void processTransfer(Transaction transaction, Account account, BigDecimal amount,
                                 UUID destinationAccountId, UUID userId) {
        if (account.getAccountType() == AccountType.CREDIT_CARD) {
            throw new BusinessException("Credit Card Transfer not allowed");
        }
        if (destinationAccountId == null) {
            throw new BusinessException("Destination Account is required for Transfer Transaction");
        }
        if (destinationAccountId.equals(account.getId())) {
            throw new BusinessException("Source and Destination accounts must be different for Transfer Transaction");
        }
        Account destination = findAccountForUser(destinationAccountId, userId);

        if(!Boolean.TRUE.equals(destination.getIsActive())) {
            throw new BusinessException("Destination Account is inactive and cannot receive new transactions");
        }
        if (!Boolean.TRUE.equals(account.getAllowNegativeBalance())
            &&account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Insufficient account balance for transfer");
        }
        account.setBalance(account.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));
        transaction.setDestinationAccount(destination);
    }

    //Helpers
    private Account findAccountForUser(UUID accountId, UUID userId) {
        return accountRepository.findByIdAndUserId(accountId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    private Category findCategoryForUser(UUID categoryId, UUID userId) {
        return categoryRepository.findByIdAndUserId(categoryId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
