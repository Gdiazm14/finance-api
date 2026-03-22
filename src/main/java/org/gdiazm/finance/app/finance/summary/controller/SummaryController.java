package org.gdiazm.finance.app.finance.summary.controller;

import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.summary.dto.MonthlySummaryResponse;
import org.gdiazm.finance.app.finance.summary.service.SummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor
public class SummaryController {
    private final SummaryService summaryService;

    @GetMapping("/{year}/{month}")
    public MonthlySummaryResponse getMonthlySummary(
            @PathVariable int year,
            @PathVariable int month
    ) {
        return summaryService.getMonthlySummary(year, month);
    }
}
