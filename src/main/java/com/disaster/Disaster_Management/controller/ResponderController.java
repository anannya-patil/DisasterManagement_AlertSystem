package com.disaster.Disaster_Management.controller;

import com.disaster.Disaster_Management.entity.Profile;
import com.disaster.Disaster_Management.entity.User;
import com.disaster.Disaster_Management.service.ProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/responder")
public class ResponderController {

    private final ProfileService profileService;

    public ResponderController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // View assigned tasks (dummy for now)
    @GetMapping("/tasks")
    public Map<String, Object> getAssignedTasks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome Responder " + user.getEmail());
        response.put("tasks", new String[]{"Task 1: Check flood area", "Task 2: Rescue team coordination"});
        response.put("status", "Active");
        return response;
    }

    // Update task progress
    @PostMapping("/tasks/update")
    public String updateTask(@RequestBody Map<String, String> request) {
        String taskId = request.get("taskId");
        String status = request.get("status");
        return "Task " + taskId + " updated to " + status + " successfully!";
    }

    // View profile
    @GetMapping("/profile")
    public Profile getMyProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return profileService.getProfileByUser(user);
    }
}