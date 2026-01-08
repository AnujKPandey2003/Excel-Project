package com.example.excel.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.excel.model.LoginRequest;
import com.example.excel.model.User;
import com.example.excel.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // REGISTER USER
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.addUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // LOGIN USER (DTO-based)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
        String token = userService.loginUser(request);
        return ResponseEntity.ok(token);
    }
}
