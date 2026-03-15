package org.gdiazm.finance.app.finance.security;

import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


public class CustomUserDetails implements UserDetails {

    private final UUID userId;
    private final User user;

    public CustomUserDetails(UUID userId) {
        this.userId = userId;
        this.user = null;
    }

    public CustomUserDetails(User user) {
        this.userId = getId();
        this.user = user;
    }

    public UUID getId() {
        return userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user != null ? user.getEmail() : userId.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
