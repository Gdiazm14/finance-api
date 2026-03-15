package org.gdiazm.finance.app.finance.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.common.entity.BaseEntity;
import org.gdiazm.finance.app.finance.user.entity.User;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    private Boolean allowNegativeBalance = false;

    @Column(nullable = false)
    private Boolean isActive = true;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
