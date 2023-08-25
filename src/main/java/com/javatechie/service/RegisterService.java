package com.javatechie.service;

import com.javatechie.dto.UserInfoDTO;
import org.springframework.http.ResponseEntity;

public interface RegisterService {
    String addNewUser(UserInfoDTO userInfoDTO);
    String verifyOTP(String email, String verificationCode);
    String verifyEmail(String email, String verificationCode);
    String resendVerificationLink(String email);
}
