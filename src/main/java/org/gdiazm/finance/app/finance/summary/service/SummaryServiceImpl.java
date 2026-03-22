package org.gdiazm.finance.app.finance.summary.service;

import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.category.entity.Category;
import org.gdiazm.finance.app.finance.category.repository.CategoryRepository;
import org.gdiazm.finance.app.finance.common.exception.BusinessException;
import org.gdiazm.finance.app.finance.security.SecurityUtils;
import org.gdiazm.finance.app.finance.summary.dto.CategorySpentProjection;
import org.gdiazm.finance.app.finance.summary.dto.CategorySummaryResponse;
import org.gdiazm.finance.app.finance.summary.dto.MonthlySummaryResponse;
import org.gdiazm.finance.app.finance.summary.repository.SummaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final SummaryRepository summaryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public MonthlySummaryResponse getMonthlySummary(int year, int month) {
        if (month < 1 || month > 12) {
            throw new BusinessException("Invalid month");
        }
        UUID userId = SecurityUtils.getCurrentUserId();

        //Rango del mes y fin exactos

        YearMonth yearMonth = YearMonth.of(year, month);
        OffsetDateTime start = yearMonth.atDay(1).atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        //Totales del mes

        BigDecimal totalIncome = summaryRepository.getTotalIncome(userId, start, end);
        BigDecimal totalExpense = summaryRepository.getTotalExpense(userId, start, end);
        BigDecimal netBalance = totalIncome.subtract(totalExpense);


        Map<UUID, BigDecimal> spentMap = summaryRepository
                .getSpentByCategory(userId,start,end)
                .stream()
                .collect(Collectors.toMap(
                        CategorySpentProjection::getCategoryId,
                        CategorySpentProjection::getSpent
                ));

        //Construir resumen por categoria
        List<CategorySummaryResponse> categories = categoryRepository
                .findByUserIdAndIsActiveTrue(userId)
                .stream()
                .map(category -> buildCategorySummary(category, spentMap))
                .toList();

        return new MonthlySummaryResponse(year, month, totalIncome, totalExpense, netBalance,categories);
    }

    private CategorySummaryResponse buildCategorySummary(Category category,
                                                         Map<UUID, BigDecimal> spentMap) {

        BigDecimal spent = spentMap.getOrDefault(category.getId(), BigDecimal.ZERO);
        BigDecimal remaining = category.getBudgetAmount().subtract(spent);
        boolean overBudget = spent.compareTo(category.getBudgetAmount()) > 0;

        return new CategorySummaryResponse(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.getBudgetAmount(),
                spent,
                remaining,
                overBudget
        );

    }
}
