package org.gdiazm.finance.app.finance.summary.service;

import org.gdiazm.finance.app.finance.summary.dto.MonthlySummaryResponse;

public interface SummaryService {
    MonthlySummaryResponse getMonthlySummary(int year, int month);
}
