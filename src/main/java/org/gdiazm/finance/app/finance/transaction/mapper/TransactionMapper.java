package org.gdiazm.finance.app.finance.transaction.mapper;

import org.gdiazm.finance.app.finance.transaction.dto.TransactionRequest;
import org.gdiazm.finance.app.finance.transaction.dto.TransactionResponse;
import org.gdiazm.finance.app.finance.transaction.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "destinationAccount", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Transaction toEntity(TransactionRequest request);

    @Mapping(target = "accountName", source = "account.name")
    @Mapping(target = "destinationAccountName", source = "destinationAccount.name")
    @Mapping(target = "categoryName", source = "category.name")
    TransactionResponse toResponse(Transaction transaction);


}
