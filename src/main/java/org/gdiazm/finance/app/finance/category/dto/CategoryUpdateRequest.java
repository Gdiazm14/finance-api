package org.gdiazm.finance.app.finance.category.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CategoryUpdateRequest {
    @Size(min = 1, max = 45)
    private String name;
    @DecimalMin(value = "0.0")
    private BigDecimal budgetAmount;
    @Size(min = 3, max = 7)
    private String color;
}
