package org.gdiazm.finance.app.finance.account.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.account.entity.AccountType;

@Getter
@Setter
public class AccountUpdateRequest {
    @Size(min = 1, max = 45)
    private String name;

    private AccountType accountType;

    private Boolean allowNegativeBalance;
}
