package org.gdiazm.finance.app.finance.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UUID uuid;
    private String name;
    private String email;
}
