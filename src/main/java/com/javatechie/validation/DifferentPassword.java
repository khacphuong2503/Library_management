package com.javatechie.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DifferentPasswordValidator.class)
public @interface DifferentPassword {
    String message() default "Passwords must be different";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
