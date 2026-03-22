package org.gdiazm.finance.app.finance.transaction.service;

import org.gdiazm.finance.app.finance.common.dto.PageResponse;
import org.gdiazm.finance.app.finance.common.entity.TransactionType;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
TransactionResponse createTransaction(TransactionRequest request);
//List<TransactionResponse> getTransactions();


    PageResponse<TransactionResponse> getTransactions(
            TransactionType type,
            UUID accountId,
            OffsetDateTime startDate,
            OffsetDateTime endDate,
            int page,
            int size
    );


TransactionResponse getTransactionById(UUID transactionId);
}
