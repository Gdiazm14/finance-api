package org.gdiazm.finance.app.finance.auth.controller;

import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.auth.dto.AuthResponse;
import org.gdiazm.finance.app.finance.auth.dto.LoginRequest;
import org.gdiazm.finance.app.finance.auth.dto.RegisterRequest;
import org.gdiazm.finance.app.finance.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
