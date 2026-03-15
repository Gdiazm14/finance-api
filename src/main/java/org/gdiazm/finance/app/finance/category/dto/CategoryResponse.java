package org.gdiazm.finance.app.finance.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
public class CategoryResponse {
private UUID id;
private String name;
private BigDecimal budgetAmount;
private String color;
private Boolean isDefault;
private Boolean isActive;
private OffsetDateTime createdAt;
}
