package org.gdiazm.finance.app.finance.account.dto;

import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.account.entity.AccountType;


import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
@Getter
@Setter
public class AccountResponse {

    private UUID id;
    private String name;
    private BigDecimal balance;
    private AccountType accountType;
    private Boolean allowNegativeBalance;
    private Boolean isActive;
    private OffsetDateTime createdAt;
}
