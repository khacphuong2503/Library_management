package com.javatechie.repository;

import com.javatechie.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String name);

    Optional<UserInfo> findByStatus(String status);

    boolean existsByEmail(String email);

    UserInfo findByEmail(String email);

    UserInfo findByPhoneNumber(String phoneNumber);

    boolean existsByEmailVerified(boolean emailVerified);

    boolean existsByOtpVerified(boolean emaiVerifiedOTP);
    boolean existsByphoneNumber(String phoneNumber);

    boolean existsByName(String name);

    UserInfo findByEmailAndVerificationCode(String email, String verificationCode);

    UserInfo findByEmailAndEmailVerified(String email, boolean emailVerified);
}
