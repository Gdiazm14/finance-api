package org.gdiazm.finance.app.finance.user.service;

import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.common.exception.BusinessException;
import org.gdiazm.finance.app.finance.security.SecurityUtils;
import org.gdiazm.finance.app.finance.user.dto.PasswordUpdateRequest;
import org.gdiazm.finance.app.finance.user.dto.UserResponse;
import org.gdiazm.finance.app.finance.user.dto.UserUpdatedRequest;
import org.gdiazm.finance.app.finance.user.entity.User;
import org.gdiazm.finance.app.finance.user.mapper.UserMapper;
import org.gdiazm.finance.app.finance.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getProfile() {
        return userMapper.toResponse(getCurrentUser());
    }

    @Override
    @Transactional
    public UserResponse updateProfile(UserUpdatedRequest request) {
        User user = getCurrentUser();

        if (request.getEmail() != null
                && !request.getEmail().equals(user.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already in use");
        }
        userMapper.updateEntityFromRequest(request, user);


        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest request) {
        User user = getCurrentUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException("Current password is incorrect");
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("New password must be different from current password");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }


    private User getCurrentUser() {
        return userRepository.findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
