package com.zynetic.bookstore.BookstoreApplication.controller;


import com.zynetic.bookstore.BookstoreApplication.dto.AuthRequest;
import com.zynetic.bookstore.BookstoreApplication.dto.AuthResponse;
import com.zynetic.bookstore.BookstoreApplication.entity.User;
import com.zynetic.bookstore.BookstoreApplication.repository.UserRepository;
import com.zynetic.bookstore.BookstoreApplication.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JWTUtil jwtUtil;

    @PostMapping("/signup")
    public String signup(@RequestBody AuthRequest request) {
        // Create and save the new user
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        return "Signup successful!";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        // Authenticate the user and return a token if successful
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}