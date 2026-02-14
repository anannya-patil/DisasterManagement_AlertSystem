package com.disaster.Disaster_Management.controller;

import com.disaster.Disaster_Management.entity.User;
import com.disaster.Disaster_Management.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users (Admin only)
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Delete user (Admin only)
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully with id: " + id;
    }

    // Simple admin dashboard info
    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "Welcome Admin! You have full access to the system.";
    }
}