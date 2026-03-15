package org.gdiazm.finance.app.finance.transaction.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.common.entity.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
public class TransactionRequest {
    private UUID accountId;
    private UUID categoryId;
    private BigDecimal amount =  BigDecimal.ZERO;
    private TransactionType type;
}
