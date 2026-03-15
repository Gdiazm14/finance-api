package org.gdiazm.finance.app.finance.transaction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.account.entity.Account;
import org.gdiazm.finance.app.finance.category.entity.Category;
import org.gdiazm.finance.app.finance.common.entity.BaseEntity;
import org.gdiazm.finance.app.finance.common.entity.TransactionType;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    private BigDecimal amount =  BigDecimal.ZERO;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
