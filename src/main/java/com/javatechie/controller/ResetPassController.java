package com.javatechie.controller;

import com.javatechie.service.ResetPassService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class ResetPassController {

    private final ResetPassService resetPassService;

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email) {
        String result = resetPassService.resetPassword(email);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam("email") String email,
                                                 @RequestParam("password") String password,
                                                 @RequestParam("confirmPassword") String confirmPassword) {
        String result = resetPassService.changePassword(email, password, confirmPassword);
        return ResponseEntity.ok(result);
    }
}
