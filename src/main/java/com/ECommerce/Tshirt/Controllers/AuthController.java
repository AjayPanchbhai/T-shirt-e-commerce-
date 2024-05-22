package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.UserDTO;
import com.ECommerce.Tshirt.Helpers.passwordValidator.ValidPasswordHelper;
import com.ECommerce.Tshirt.Mappers.UserMapper;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import com.ECommerce.Tshirt.Services.OTPService;
import com.ECommerce.Tshirt.Utilities.JwtUtil;
import jakarta.mail.MessagingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auths")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OTPService otpService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @NotNull("Email and Password Required!") User user) {
        String validationMessage = ValidPasswordHelper.isValid(user.getPassword());

        if(!validationMessage.equalsIgnoreCase("valid"))
            throw new IllegalArgumentException(validationMessage);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok().body(UserMapper.toUserDTO(userRepository.save(user)));
    }

    @PostMapping("/signin")
    public String createAuthenticationToken(@RequestBody User user) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return "Auth Token : " + jwt;
    }

    @PostMapping("/sendOTP")
    public ResponseEntity<String> sendOTP(@RequestParam String email) {
        try {
            otpService.sendOTP(email);
            return ResponseEntity.ok("OTP sent to your email.");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error sending OTP.");
        }
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<String> verifyOTP(@RequestParam String email, @RequestParam String OTP) {
        if (otpService.verifyOTP(email, OTP)) {
            otpService.removeOTP(email);
            return ResponseEntity.ok("OTP verified successfully. User logged in.");
        } else {
            return ResponseEntity.status(400).body("Invalid OTP.");
        }
    }
}
