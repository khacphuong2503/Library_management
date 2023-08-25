package com.javatechie.validation;

import com.javatechie.dto.ChangePasswordRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DifferentPasswordValidator implements ConstraintValidator<DifferentPassword, ChangePasswordRequestDTO> {

    @Override
    public boolean isValid(ChangePasswordRequestDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String password = value.getCurrentPassword();
        String newPassword = value.getNewPassword();

        if (password == null || newPassword == null) {
            return true;
        }

        return !password.equals(newPassword);
    }
}