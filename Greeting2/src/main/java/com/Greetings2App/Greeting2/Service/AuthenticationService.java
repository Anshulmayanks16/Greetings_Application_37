package com.Greetings2App.Greeting2.Service;

import com.Greetings2App.Greeting2.Repository.AuthUserRepository;

import com.Greetings2App.Greeting2.Models.AuthUser;


import com.Greetings2App.Greeting2.UserDTO.AuthUserDTO;
import com.Greetings2App.Greeting2.UserDTO.LoginDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.Greetings2App.Greeting2.Security.JWTUtil;

import java.util.*;
@Service
public class AuthenticationService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public AuthenticationService(AuthUserRepository authUserRepository, JWTUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String registerUser(AuthUserDTO authUserDTO) {
        Optional<AuthUser> existingUser = authUserRepository.findByEmailIgnoreCase(authUserDTO.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        AuthUser user = new AuthUser();
        user.setFirstName(authUserDTO.getFirstName());
        user.setFirstName(authUserDTO.getLastName());
        user.setFirstName(authUserDTO.getEmail());
        user.setPassword(passwordEncoder.encode(authUserDTO.getPassword()));

        authUserRepository.save(user);
        return "User registered Successfully";


    }

    public String loginUser(LoginDTO loginDTO) {
        System.out.println("Checking email: " + loginDTO.getEmail());

        Optional<AuthUser> userOptional = authUserRepository.findByEmailIgnoreCase(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            System.out.println("User not found for email: " + loginDTO.getEmail());
            throw new RuntimeException("User not found");
        }

        AuthUser user = userOptional.get();
        System.out.println("User found: " + user.getEmail());

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
