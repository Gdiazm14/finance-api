package org.gdiazm.finance.app.finance.summary.dto;

import java.math.BigDecimal;
import java.util.UUID;

public interface CategorySpentProjection {
    UUID getCategoryId();
    BigDecimal getSpent();
}
