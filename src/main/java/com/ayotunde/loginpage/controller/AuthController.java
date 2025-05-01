package com.ayotunde.loginpage.controller;

import com.ayotunde.loginpage.model.AuthRequest;
import com.ayotunde.loginpage.model.AuthResponse;
import com.ayotunde.loginpage.model.Register;
import com.ayotunde.loginpage.repositories.UserRepo;
import com.ayotunde.loginpage.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Register register) {
        Optional<Register> existingUser = userRepo.findById(register.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        register.setPassword(passwordEncoder.encode(register.getPassword())); // Encrypt the password
        userRepo.save(register);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    // Login user and generate JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest authRequest) {
        Optional<Register> user = userRepo.findByEmail(authRequest.getEmail());


        if (user.isEmpty() || !passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(user.get().getId());
        AuthResponse authResponse = new AuthResponse(token);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
