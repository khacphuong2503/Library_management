package com.javatechie.service.Impl;

import com.javatechie.dto.UserInfoDTO;
import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import com.javatechie.service.ProductService;
import com.javatechie.service.RegisterService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Validated
public class RegisterServiceImpl implements RegisterService {

    private final UserInfoRepository userInfoRepository;
    private final JavaMailSender javaMailSender;
    private final ProductService productService;
//    private final ValidatorService validatorService;

    @Override
    @Transactional
    public String addNewUser(@Valid UserInfoDTO userInfoDTO) {
        String name = userInfoDTO.getName();
        String email = userInfoDTO.getEmail();
        String phoneNumber = userInfoDTO.getPhoneNumber();
        String password = userInfoDTO.getPassword();
        String verificationCode = generateVerificationCode();

        if (userInfoRepository.existsByName(name) &&
                userInfoRepository.existsByEmail(email) &&
                userInfoRepository.existsByphoneNumber(phoneNumber) &&
                !userInfoRepository.existsByEmailVerified(true)) {
            return "Account already exists";
        }

        if (userInfoRepository.existsByName(name) &&
                userInfoRepository.existsByEmail(email) &&
                userInfoRepository.existsByphoneNumber(phoneNumber) &&
                userInfoRepository.existsByEmailVerified(true)) {
            return "The account has been successfully registered, please verify your email.";
        }

        if (userInfoRepository.existsByName(name)) {
            return "User Name has been posted with another account";
        }

        UserInfo existingUser = userInfoRepository.findByEmailAndEmailVerified(email, true);
        if (existingUser != null) {
            return "Email has already been verified.";
        }

        if (userInfoRepository.existsByEmail(email)) {
            return "Email has been posted with another account";
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setEmail(email);
        userInfo.setPhoneNumber(phoneNumber);
        userInfo.setPassword(password);
        userInfo.setVerificationCode(verificationCode);
        String response = productService.addUser(userInfo);

        sendVerificationEmail(email, verificationCode);

        return "Registration successful. Please check your email for verification.";
    }

    @Override
    @Transactional
    public String verifyOTP(String email, String verificationCode) {
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if (userInfo == null) {
            return "User not found";
        }

        if (userInfo.isOtpVerified()) {
            return "Email already verified";
        }

        if (userInfo.getVerificationCode().equals(verificationCode)) {
            userInfo.setOtpVerified(true);
            userInfoRepository.save(userInfo);
            return "OTP verification successful. Email verified.";
        }

        return "OTP verification failed.";
    }

    @Override
    @Transactional
    public String verifyEmail(String email, String verificationCode) {
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if (userInfo == null) {
            return "User not found";
        }

        if (userInfo.isEmailVerified()) {
            return "Email already verified";
        }

        if (userInfo.getVerificationCode().equals(verificationCode)) {
            userInfo.setEmailVerified(true);
            userInfoRepository.save(userInfo);
            return "Verify email link is successful. Email verified.";
        }

        return "Verify email link is invalid.";
    }

    @Override
    @Transactional
    public String resendVerificationLink(String email) {
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if (userInfo == null) {
            return "User not found";
        }

        if (userInfo.isEmailVerified()) {
            return "Email already verified";
        }

        String verificationCode = generateVerificationCode();
        userInfo.setVerificationCode(verificationCode);
        userInfoRepository.save(userInfo);

        // Send verification email
        sendVerificationEmail(email, verificationCode);

        return "Verification email resent successfully.";
    }

    private void sendVerificationEmail(String email, String verificationCode) {

        LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(60);
        UserInfo user = userInfoRepository.findByEmail(email);
        user.setVerificationCode(verificationCode);
        user.setVerificationExpirationTime(expirationTime);
        userInfoRepository.save(user);

        String verificationLink = "http://localhost:8080/library/verifyEmail?email=" +
                email + "&code=" + verificationCode + "&expiration=" + expirationTime;

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Email Verification");

            String emailContent = "<p>Please click the following link to verify your email:</p>" +
                    "<a href=\"" + verificationLink + "\">Verify Email</a>" +
                    "<p>And enter the following OTP:</p>" +
                    "<p>OTP: " + verificationCode + "</p>";

            helper.setText(emailContent, true);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        String verificationCode = UUID.randomUUID().toString().replaceAll("-", "");
        return verificationCode;
    }
}