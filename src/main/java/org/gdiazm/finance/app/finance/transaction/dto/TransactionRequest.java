package org.gdiazm.finance.app.finance.transaction.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private UUID destinationAccountId;
    private UUID categoryId;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
    @NotNull
    private TransactionType type;
    @Size(max = 255)
    private String note;
}
