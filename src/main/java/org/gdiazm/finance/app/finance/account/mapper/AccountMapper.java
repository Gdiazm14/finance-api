package org.gdiazm.finance.app.finance.account.mapper;

import org.gdiazm.finance.app.finance.account.dto.AccountRequest;
import org.gdiazm.finance.app.finance.account.dto.AccountResponse;
import org.gdiazm.finance.app.finance.account.dto.AccountUpdateRequest;
import org.gdiazm.finance.app.finance.account.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Account toEntity(AccountRequest request);

    AccountResponse toResponse(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "balance", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(AccountUpdateRequest request, @MappingTarget Account account);
}



