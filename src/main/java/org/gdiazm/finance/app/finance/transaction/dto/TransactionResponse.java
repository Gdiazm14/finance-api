package org.gdiazm.finance.app.finance.transaction.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.common.entity.TransactionType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class TransactionResponse {
    private UUID id;
    private BigDecimal amount;
    private TransactionType type;
    private String note;
    private String accountName;
    private String destinationAccountName;
    private String categoryName;
    private OffsetDateTime createdAt;
}
