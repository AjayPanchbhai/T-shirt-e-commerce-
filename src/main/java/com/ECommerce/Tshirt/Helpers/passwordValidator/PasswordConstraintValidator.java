package com.ECommerce.Tshirt.Helpers.passwordValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[^a-zA-Z0-9].*");

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.trim().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password is Required!").addConstraintViolation();
            return false;
        }

        if (password.length() < 6) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password should be minimum 6 characters").addConstraintViolation();
            return false;
        }

        if (!LOWERCASE_PATTERN.matcher(password).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain one lower case").addConstraintViolation();
            return false;
        }

        if (!UPPERCASE_PATTERN.matcher(password).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain one upper case").addConstraintViolation();
            return false;
        }

        if (!DIGIT_PATTERN.matcher(password).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain one digit").addConstraintViolation();
            return false;
        }

        if (!SPECIAL_CHARACTER_PATTERN.matcher(password).matches()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password must contain one special character").addConstraintViolation();
            return false;
        }

        return true;
    }
}

