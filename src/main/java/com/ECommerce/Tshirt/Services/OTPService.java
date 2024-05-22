package com.ECommerce.Tshirt.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class OTPService {

    @Autowired
    private JavaMailSender mailSender;

    private final Map<String, String> otpStorage = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public String generateOTP() {
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    public void sendOTP(String email) throws MessagingException {
        String otp = generateOTP();
        otpStorage.put(email, otp);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Your OTP Code");
        helper.setText("Your OTP code is " + otp, true);

        mailSender.send(message);
    }

    public boolean verifyOTP(String email, String otp) {
        return otp.equals(otpStorage.get(email));
    }

    public void removeOTP(String email) {
        otpStorage.remove(email);
    }
}

