package org.gdiazm.finance.app.finance.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdatedRequest {
    @Size(min = 2, max = 50)
    private String name;
    @Email
    private String email;
}
