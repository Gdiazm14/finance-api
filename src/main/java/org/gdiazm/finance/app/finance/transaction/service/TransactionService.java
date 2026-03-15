package org.gdiazm.finance.app.finance.transaction.service;

import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;

public interface TransactionService {
TransactionResponse createTransaction(TransactionRequest request);
}
