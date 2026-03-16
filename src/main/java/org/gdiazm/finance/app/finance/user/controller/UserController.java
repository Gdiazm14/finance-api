package org.gdiazm.finance.app.finance.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.user.dto.PasswordUpdateRequest;
import org.gdiazm.finance.app.finance.user.dto.UserResponse;
import org.gdiazm.finance.app.finance.user.dto.UserUpdatedRequest;
import org.gdiazm.finance.app.finance.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getProfile() {
        return userService.getProfile();
    }

    @PatchMapping("/me")
    public UserResponse updateProfile(@Valid @RequestBody UserUpdatedRequest request) {
        return userService.updateProfile(request);
    }

    @PatchMapping("/me/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updatePassword(@Valid @RequestBody PasswordUpdateRequest request) {
        userService.updatePassword(request);
    }

}
