package org.gdiazm.finance.app.finance.security;

import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtils {

    public static UUID getCurrentUserId() {
        CustomUserDetails userDetails = (CustomUserDetails)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userDetails.getId();
    }
}
