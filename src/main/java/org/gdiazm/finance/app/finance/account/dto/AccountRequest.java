package org.gdiazm.finance.app.finance.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.account.entity.AccountType;

@Getter
@Setter
public class AccountRequest {


    @NotBlank
    @Size(min = 1, max = 45)
    private String name;

    @NotNull
    private AccountType accountType;

    private Boolean allowNegativeBalance = false;
}
