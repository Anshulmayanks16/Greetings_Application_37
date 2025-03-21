package com.Greetings2App.Greeting2.Service;

import com.Greetings2App.Greeting2.Repository.AuthUserRepository;

import com.Greetings2App.Greeting2.Models.AuthUser;


import com.Greetings2App.Greeting2.UserDTO.AuthUserDTO;
import com.Greetings2App.Greeting2.UserDTO.LoginDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.Greetings2App.Greeting2.Security.JWTUtil;

import java.sql.SQLOutput;
import java.util.*;
@Service
public class AuthenticationService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final EmailService emailService;

    public AuthenticationService(AuthUserRepository authUserRepository, JWTUtil jwtUtil, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    public String registerUser(AuthUserDTO authUserDTO) {
        Optional<AuthUser> existingUser = authUserRepository.findByEmail(authUserDTO.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        AuthUser user = new AuthUser();
        user.setFirstName(authUserDTO.getFirstName());
        user.setLastName(authUserDTO.getLastName()); // Fix: Using correct setter
        user.setEmail(authUserDTO.getEmail()); // Fix: Using correct setter
        user.setPassword(passwordEncoder.encode(authUserDTO.getPassword()));

        authUserRepository.save(user);
        return "User registered Successfully";
    }


    public String loginUser(LoginDTO loginDTO) {
        System.out.println("Checking email: " + loginDTO.getEmail());

        Optional<AuthUser> userOptional = authUserRepository.findByEmail(loginDTO.getEmail());
        System.out.println("User Found: " + userOptional.isPresent());

        if (userOptional.isPresent()) {
            AuthUser user = userOptional.get();

            if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return "Invalid Credentials";
            }

            String token = jwtUtil.generateToken(loginDTO.getEmail());
            System.out.println("Generated Token: " + token);

            if (token != null && !token.isEmpty()) {
                String subject = "Login Alert - Your Account";
                String body = "<h3>Dear " + user.getFirstName() + ",</h3>"
                        + "<p>Your account was just accessed.</p>"
                        + "<p>If this was not you, please reset your password immediately.</p>"
                        + "<p>Best regards, <br> Lauda leleeee</p>";

                boolean emailSent = emailService.sendEmail(user.getEmail(), subject, body);
                System.out.println("Email Sent: " + emailSent);

                return "Login Successful\n" + token;
            }
        }
        return "Invalid Credentials";
    }
}




