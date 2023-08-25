package com.javatechie.controller;

import com.javatechie.dto.ChangeEmailRequestDTO;
import com.javatechie.dto.ChangePasswordRequestDTO;
import com.javatechie.dto.ChangePhoneNumberRequestDTO;
import com.javatechie.dto.VerifyChangeEmailRequestDTO;
import com.javatechie.dto.VerifyChangePhoneNumberDTO;
import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import com.javatechie.service.ChangeEmailService;
import com.javatechie.service.ChangePasswordService;
import com.javatechie.service.ChangePhoneNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class UserController {
    private final ChangePasswordService changePasswordService;
    private final UserInfoRepository userInfoRepository;
    private final ChangeEmailService emailChangeService;
    private final ChangePhoneNumberService changePhoneNumberService;

    @PostMapping("/changePW")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequestDTO changePasswordRequest, Authentication authentication) {
        String result = changePasswordService.changePassword(changePasswordRequest, authentication);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changeEmail")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> changeEmail(@RequestBody ChangeEmailRequestDTO changeEmailRequest, Authentication authentication) {
        String currentUsername = authentication.getName();
        Optional<UserInfo> currentUserOptional = userInfoRepository.findByName(currentUsername);

        if (currentUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        UserInfo currentUser = currentUserOptional.get();
        String newEmail = changeEmailRequest.getEmail();

        String result = emailChangeService.updateEmail(currentUser, newEmail);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/verifyChangeEmail")
    public ResponseEntity<String> verifyChangeEmail(@RequestBody VerifyChangeEmailRequestDTO verifyChangeEmailRequest, Authentication authentication) {
        String currentUsername = authentication.getName();
        Optional<UserInfo> currentUserOptional = userInfoRepository.findByName(currentUsername);

        if (currentUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        UserInfo currentUser = currentUserOptional.get();

        String result = emailChangeService.verifyEmailChange(currentUser, verifyChangeEmailRequest);
        return ResponseEntity.ok(result);
    }



    @PostMapping("/changePhoneNumber")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> changePhoneNumber(@RequestBody ChangePhoneNumberRequestDTO changePhoneNumberRequest, Authentication authentication) {
        String currentUsername = authentication.getName();
        Optional<UserInfo> currentUserOptional = userInfoRepository.findByName(currentUsername);

        if (currentUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        UserInfo currentUser = currentUserOptional.get();
        String newPhoneNumber = changePhoneNumberRequest.getPhoneNumber();

        String result = changePhoneNumberService.updatePhoneNumber(currentUser, newPhoneNumber);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/verifyChangePhoneNumber")
    public ResponseEntity<String> verifyChangePhoneNumber(@RequestBody VerifyChangePhoneNumberDTO verifyChangePhoneNumberRequest, Authentication authentication) {
        String currentUsername = authentication.getName();
        Optional<UserInfo> currentUserOptional = userInfoRepository.findByName(currentUsername);

        if (currentUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        UserInfo currentUser = currentUserOptional.get();

//        String enteredOtp = verifyChangePhoneNumberRequest.getOtp();

        String result = changePhoneNumberService.verifyPhoneNumberChange(currentUser, verifyChangePhoneNumberRequest);
        return ResponseEntity.ok(result);
    }
}