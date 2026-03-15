package org.gdiazm.finance.app.finance.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.gdiazm.finance.app.finance.common.entity.BaseEntity;
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
}
