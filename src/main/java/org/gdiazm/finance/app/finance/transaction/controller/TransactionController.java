package org.gdiazm.finance.app.finance.transaction.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }
    @GetMapping
    public List<TransactionResponse> getTransactions() {
        return transactionService.getTransactions();
    }
    @GetMapping("/{id}")
    public TransactionResponse getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

}
