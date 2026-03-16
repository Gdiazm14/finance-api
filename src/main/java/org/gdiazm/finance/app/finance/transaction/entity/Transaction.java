package org.gdiazm.finance.app.finance.transaction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.account.entity.Account;
import org.gdiazm.finance.app.finance.category.entity.Category;
import org.gdiazm.finance.app.finance.common.entity.BaseEntity;
import org.gdiazm.finance.app.finance.common.entity.TransactionType;
import org.gdiazm.finance.app.finance.user.entity.User;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(length = 255)
    private String note;

    @ManyToOne(fetch = FetchType.LAZY,  optional = false)
    @JoinColumn(name = "account_id",  nullable = false)
    private Account account;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;
}
