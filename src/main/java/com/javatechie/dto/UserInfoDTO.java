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

    @NotEmpty(message = "Thiếu password")
    @Size(min = 8, message = "Password phải từ 8 kí tự trở lên")
    private String password;

    @NotEmpty(message = "Chưa điền sđt bạn ơi")
    @Pattern(regexp = "^[+]?[0-9]{10,13}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotNull(message = "Thiếu Email")
    @Email(message = "Email không hợp lệ")
    private String email;
}