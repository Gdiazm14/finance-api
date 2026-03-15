package org.gdiazm.finance.app.finance.category.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.common.entity.BaseEntity;
import org.gdiazm.finance.app.finance.user.entity.User;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Column(nullable = false)
    private String name; // Ej: Ahorro, Comida, Renta

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal budgetAmount =  BigDecimal.ZERO;

    @Column(length = 7)
    private String color;

    @Column(nullable = false)
    private Boolean isDefault = false;

    @Column(nullable = false)
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;
}
