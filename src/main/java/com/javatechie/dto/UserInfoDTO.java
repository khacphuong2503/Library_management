package com.javatechie.dto;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserInfoDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotEmpty(message = "Please enter a password")
    @Size(min = 8, message = "Password must be 8 characters or more")
    private String password;

    @NotEmpty(message = "Please enter the phone number")
    @Pattern(regexp = "^[+]?[0-9]{10,13}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotNull(message = "Please enter your email")
    @Email(message = "Invalid email")
    private String email;
}
