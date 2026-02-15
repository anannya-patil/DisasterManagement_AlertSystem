package com.disaster.Disaster_Management.service;

import com.disaster.Disaster_Management.config.JwtUtil;
import com.disaster.Disaster_Management.entity.Role;
import com.disaster.Disaster_Management.entity.User;
import com.disaster.Disaster_Management.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(String email, String password, String role) {
        if(userRepository.findByEmail(email).isPresent()) {
            return "Email already exists";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.valueOf(role.toUpperCase()));

        userRepository.save(user);
        return "User registered successfully";
    }

    public ResponseEntity<?> login(String email, String password) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid email or password"));

            if(!passwordEncoder.matches(password, user.getPassword())) {
                throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid email or password");
            }

            String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
            return ResponseEntity.ok(token);
            
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR, "Login failed", e);
        }
    }
}