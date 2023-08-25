package com.javatechie.service.Impl;

import com.javatechie.dto.ChangePasswordRequestDTO;
import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import com.javatechie.service.ChangePasswordService;
import com.javatechie.validation.DifferentPassword;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;


import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final UserInfoRepository userInfoRepository;
    private final Validator validator;

    @Override
    @Transactional
    @Validated
    public String changePassword(@Valid ChangePasswordRequestDTO changePasswordRequest, Authentication authentication) {
        String username = authentication.getName();

        if (username == null) {
            return "User is not authenticated.";
        }

        Optional<UserInfo> optionalUser = userInfoRepository.findByName(username);

        if (optionalUser.isEmpty()) {
            return "Invalid user.";
        }

        UserInfo user = optionalUser.get();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            return "Invalid current password.";
        }

        String encodedNewPassword = passwordEncoder.encode(changePasswordRequest.getNewPassword());
        user.setPassword(encodedNewPassword);
        userInfoRepository.save(user);

        return "Password changed successfully. Please log in again with your new password.";
    }
}