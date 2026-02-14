package com.disaster.Disaster_Management.service;

import com.disaster.Disaster_Management.config.JwtUtil;
import com.disaster.Disaster_Management.entity.Role;
import com.disaster.Disaster_Management.entity.User;
import com.disaster.Disaster_Management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(String email, String password, String role)
    {
        if(userRepository.findByEmail(email).isPresent())
        {
            return "Email already exists";
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.valueOf(role.toUpperCase()));

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(String email, String password)
{
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Invalid email or password"));

    if(!passwordEncoder.matches(password, user.getPassword()))
    {
        throw new RuntimeException("Invalid email or password");
    }

    // Generate token with ALL three parameters
    String token = jwtUtil.generateToken(
        user.getId(), 
        user.getEmail(), 
        user.getRole().name()  // This should be "ADMIN", "RESPONDER", or "CITIZEN"
    );
    
    System.out.println("Login successful for: " + email);
    System.out.println("Generated token: " + token);
    
    return token;
}
}