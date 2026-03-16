package org.gdiazm.finance.app.finance.transaction.service;

import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
TransactionResponse createTransaction(TransactionRequest request);
List<TransactionResponse> getTransactions();
TransactionResponse getTransactionById(UUID transactionId);
}
