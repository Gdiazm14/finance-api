package org.gdiazm.finance.app.finance.summary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CategorySummaryResponse {
private UUID categoryId;
private String categoryName;
private String color;
    private BigDecimal budgetAmount; //Presupuesto del sobre
    private BigDecimal spent; //lo que se gastó en el mes
    private BigDecimal remaining; // budgetAmount - spent
    private boolean overBudget; //si spent > budgetAmount

}
