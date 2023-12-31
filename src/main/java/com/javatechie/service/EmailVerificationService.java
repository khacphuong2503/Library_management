package com.javatechie.service;

public interface EmailVerificationService {
    void sendVerificationEmail(String email, String currentOtp);
    void sendVerificationEmailNew(String email, String newOtp);
    boolean verifyOTP(String email, String currentOtp, String newOtp);
}