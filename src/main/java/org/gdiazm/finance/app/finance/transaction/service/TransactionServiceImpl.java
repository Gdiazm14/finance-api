package org.gdiazm.finance.app.finance.transaction.service;

import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.account.entity.Account;
import org.gdiazm.finance.app.finance.account.repository.AccountRepository;
import org.gdiazm.finance.app.finance.category.entity.Category;
import org.gdiazm.finance.app.finance.category.repository.CategoryRepository;
import org.gdiazm.finance.app.finance.common.entity.TransactionType;
import org.gdiazm.finance.app.finance.common.exception.BusinessException;
import org.gdiazm.finance.app.finance.common.exception.ResourceNotFoundException;
import org.gdiazm.finance.app.finance.security.SecurityUtils;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.gdiazm.finance.app.finance.transaction.mapper.TransactionMapper;
import org.gdiazm.finance.app.finance.transaction.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request) {
        Account account = getUserAccount(request.getAccountId());

        Category category = getCategory(request.getCategoryId());

        BigDecimal amount = request.getAmount();


        if (request.getType() == TransactionType.EXPENSE) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new BusinessException("Insufficient account balance");
            }
            if (category.getBudgetAmount().compareTo(amount) < 0) {
                throw new BusinessException("Insufficient category balance");
            }
        }

        if (request.getType() == TransactionType.EXPENSE) {

            account.setBalance(account.getBalance().subtract(amount));
            category.setBudgetAmount(category.getBudgetAmount().subtract(amount));
        } else if (request.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().add(amount));
        }


        Transaction transaction = transactionMapper.toEntity(request);

        transaction.setAccount(account);
        transaction.setCategory(category);

        Transaction result = transactionRepository.save(transaction);
        return transactionMapper.toTransactionResponse(result);
    }

    private Account getUserAccount(UUID uuid) {
        return accountRepository.findByIdAndUserId(uuid, SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    private Category getCategory(UUID uuid) {
        return categoryRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
