package com.ECommerce.Tshirt.Helpers.passwordValidator;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidPasswordHelper {
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile(".*[a-z].*");
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(".*\\d.*");
    private static final Pattern SPECIAL_CHARACTER_PATTERN = Pattern.compile(".*[^a-zA-Z0-9].*");

    public static String isValid(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "Password is Required!";
        }

        if (password.length() < 6) {
            return "Password should be minimum 6 characters";
        }

        if (!LOWERCASE_PATTERN.matcher(password).matches()) {
            return "Password must contain one lower case";
        }

        if (!UPPERCASE_PATTERN.matcher(password).matches()) {
            return "Password must contain one upper case";
        }

        if (!DIGIT_PATTERN.matcher(password).matches()) {
            return "Password must contain one digit";
        }

        if (!SPECIAL_CHARACTER_PATTERN.matcher(password).matches()) {
            return "Password must contain one special character";
        }

        return "valid";
    }
}
