package org.gdiazm.finance.app.finance.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequest {
    @NotBlank
    private String currentPassword;
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}
