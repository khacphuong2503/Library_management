package com.javatechie.controller;

import com.javatechie.dto.UserInfoDTO;
import com.javatechie.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/signUp")
    public ResponseEntity<String> addNewUser(@Valid @RequestBody  UserInfoDTO userInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        String response = registerService.addNewUser(userInfoDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verifyOTP")
    public ResponseEntity<String> verifyOTP(@RequestParam("email") String email, @RequestParam("otp") String verificationCode) {
        String response = registerService.verifyOTP(email, verificationCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<String> verifyEmail(@RequestParam("email") String email, @RequestParam("code") String verificationCode) {
        String response = registerService.verifyEmail(email, verificationCode);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resendVerificationLink")
    public ResponseEntity<String> resendVerificationLink(@RequestParam("email") String email) {
        String response = registerService.resendVerificationLink(email);
        return ResponseEntity.ok(response);
    }
}