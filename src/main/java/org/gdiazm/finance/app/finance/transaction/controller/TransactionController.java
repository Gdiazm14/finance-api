package org.gdiazm.finance.app.finance.transaction.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.common.dto.PageResponse;
import org.gdiazm.finance.app.finance.common.entity.TransactionType;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
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
    public PageResponse<TransactionResponse> getTransactions(@RequestParam(required = false) TransactionType type, @RequestParam(required = false) UUID accountId, @RequestParam(required = false) OffsetDateTime startDate, @RequestParam(required = false) OffsetDateTime endDate, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return transactionService.getTransactions(type, accountId, startDate, endDate, page, size);
    }


    @GetMapping("/{id}")
    public TransactionResponse getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

}
