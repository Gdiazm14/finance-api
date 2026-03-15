package org.gdiazm.finance.app.finance.auth.service;

import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.auth.dto.AuthResponse;
import org.gdiazm.finance.app.finance.auth.dto.LoginRequest;
import org.gdiazm.finance.app.finance.auth.dto.RegisterRequest;
import org.gdiazm.finance.app.finance.category.service.CategoryService;
import org.gdiazm.finance.app.finance.security.jwt.service.JwtService;
import org.gdiazm.finance.app.finance.user.entity.User;
import org.gdiazm.finance.app.finance.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CategoryService categoryService;

    public AuthResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User userSaved = userRepository.save(user);
        categoryService.setDefaultCategory(userSaved);

        String token = jwtService.generateToken(user.getId());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = getUser(loginRequest.getEmail());

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Credentials");
        }

        return generateAuthResponse(user);
    }

    private AuthResponse generateAuthResponse(User user) {
        String token = jwtService.generateToken(user.getId());
        return new AuthResponse(token);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid Credentials"));
    }

}
