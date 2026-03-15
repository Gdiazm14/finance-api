package org.gdiazm.finance.app.finance.transaction.mapper;

import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "category.name", target = "categoryName")
    TransactionResponse toTransactionResponse(Transaction transaction);

    Transaction toEntity(TransactionRequest request);
}
