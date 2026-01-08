package com.example.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.excel.Repository.UserRepo;
import com.example.excel.config.JwtUtils;
import com.example.excel.model.LoginRequest;
import com.example.excel.model.User;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder,
                       JwtUtils jwtUtils) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    // REGISTER USER
    public User addUser(User user) {

        // Check if email already exists
        if (userRepo.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already registered");
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    // LOGIN USER (DTO-based)
    public String loginUser(LoginRequest request) {

        // Find user by email
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT token
        return jwtUtils.generateToken(user);
    }
}
