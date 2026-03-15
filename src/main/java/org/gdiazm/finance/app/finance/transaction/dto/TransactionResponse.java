package org.gdiazm.finance.app.finance.transaction.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransactionResponse {
    private UUID id;
    private BigDecimal amount;
    private String type;
    private String accountName;
    private String categoryName;
}
