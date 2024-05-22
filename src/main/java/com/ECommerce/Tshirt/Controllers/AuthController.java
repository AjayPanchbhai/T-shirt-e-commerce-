package com.ECommerce.Tshirt.Controllers;

import com.ECommerce.Tshirt.DTO.UserDTO;
import com.ECommerce.Tshirt.Helpers.passwordValidator.ValidPasswordHelper;
import com.ECommerce.Tshirt.Mappers.UserMapper;
import com.ECommerce.Tshirt.Models.User;
import com.ECommerce.Tshirt.Repositories.UserRepository;
import com.ECommerce.Tshirt.Utilities.JwtUtil;
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
@RequestMapping("/api")
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
}
