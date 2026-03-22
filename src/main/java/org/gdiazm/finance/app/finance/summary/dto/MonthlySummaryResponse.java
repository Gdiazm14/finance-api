package org.gdiazm.finance.app.finance.summary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class MonthlySummaryResponse {
private int year;
private int month;
private BigDecimal totalIncome;
private BigDecimal totalExpense;
private BigDecimal netBalance; //total income - expense
private List<CategorySummaryResponse> categories;
}
