package org.gdiazm.finance.app.finance.user.service;

import org.gdiazm.finance.app.finance.user.dto.PasswordUpdateRequest;
import org.gdiazm.finance.app.finance.user.dto.UserResponse;
import org.gdiazm.finance.app.finance.user.dto.UserUpdatedRequest;

public interface UserService {

    UserResponse getProfile();
    UserResponse updateProfile(UserUpdatedRequest request);

    void updatePassword(PasswordUpdateRequest request);

}
