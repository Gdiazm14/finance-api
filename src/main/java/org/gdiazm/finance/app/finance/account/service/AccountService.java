package org.gdiazm.finance.app.finance.account.service;

import org.gdiazm.finance.app.finance.account.dto.AccountRequest;
import org.gdiazm.finance.app.finance.account.dto.AccountResponse;
import org.gdiazm.finance.app.finance.account.dto.AccountUpdateRequest;
import org.gdiazm.finance.app.finance.account.entity.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountResponse createAccount(AccountRequest request);
    List<AccountResponse> getAccounts(Boolean active);
    AccountResponse getAccountById(UUID accountId);
    AccountResponse updateAccount(UUID accountId,AccountUpdateRequest request);
    void deleteAccountById(UUID accountId);

}
