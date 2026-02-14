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
@RequestMapping("/citizen")
public class CitizenController {

    private final ProfileService profileService;

    public CitizenController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Simple test endpoint (public for testing)
    @GetMapping("/test")
    public String test() {
        return "Citizen controller test endpoint works!";
    }

    // View alerts - requires CITIZEN role
    @GetMapping("/alerts")
    public Map<String, Object> getAlerts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("🔍 Auth in /citizen/alerts: " + auth);
        System.out.println("🔍 Principal: " + auth.getPrincipal());
        System.out.println("🔍 Authorities: " + auth.getAuthorities());
        
        User user = (User) auth.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello " + user.getEmail());
        response.put("alerts", new String[]{
            "⚠️ Flood alert in Hyderabad", 
            "⚠️ Earthquake warning in Mumbai"
        });
        return response;
    }

    // Request rescue
    @PostMapping("/request-rescue")
    public String requestRescue(@RequestBody Map<String, String> request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        String location = request.get("location");
        String message = request.get("message");
        
        return "Rescue request sent from " + location + " by " + user.getEmail() + 
               ". Message: " + message + ". Help is on the way!";
    }

    // View my profile
    @GetMapping("/my-profile")
    public Profile getMyProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return profileService.getProfileByUser(user);
    }

    // Report incident
    @PostMapping("/report-incident")
    public String reportIncident(@RequestBody Map<String, String> request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        
        String type = request.get("type");
        String location = request.get("location");
        String description = request.get("description");
        
        return "Incident reported: " + type + " at " + location + " by " + user.getEmail() + 
               ". Description: " + description + ". Authorities notified!";
    }
}