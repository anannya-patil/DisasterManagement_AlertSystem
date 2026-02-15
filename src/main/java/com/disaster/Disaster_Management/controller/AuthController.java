package com.disaster.Disaster_Management.controller;

import com.disaster.Disaster_Management.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        return authService.register(
                request.get("email"),
                request.get("password"),
                request.get("role")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        return authService.login(
                request.get("email"),
                request.get("password")
        );
    }
}