package com.prathama.expensetracker.controller;

import com.prathama.expensetracker.model.User;
import com.prathama.expensetracker.repository.UserRepository;
import com.prathama.expensetracker.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping()
    public String home() {
        return "Welcome to Expense Tracker API!";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return jwtUtil.generateToken(user.getUsername());
    }
}
