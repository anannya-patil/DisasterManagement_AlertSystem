package com.disaster.Disaster_Management.service;

import com.disaster.Disaster_Management.entity.Profile;
import com.disaster.Disaster_Management.entity.User;
import com.disaster.Disaster_Management.repository.ProfileRepository;
import com.disaster.Disaster_Management.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository,
                          UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    // ✅ Create profile for logged-in user only
    public ResponseEntity<?> createProfile(Profile profile) {
        try {
            // 1️⃣ Get logged-in User from SecurityContext
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = (User) authentication.getPrincipal();

            // 2️⃣ Check for duplicate profile
            if (profileRepository.existsByUser(user)) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Profile already exists for this user");
            }

            // 3️⃣ Link profile to user
            profile.setUser(user);

            // 4️⃣ Save profile
            Profile savedProfile = profileRepository.save(profile);
            return ResponseEntity.ok(savedProfile);
            
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating profile: " + e.getMessage());
        }
    }

    // Get all profiles
    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    // Update profile
    public ResponseEntity<?> update(Long id, Profile updatedProfile) {
        try {
            Profile existing = profileRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Profile not found"));

            existing.setFullName(updatedProfile.getFullName());
            existing.setPhone(updatedProfile.getPhone());
            existing.setRegion(updatedProfile.getRegion());

            Profile saved = profileRepository.save(existing);
            return ResponseEntity.ok(saved);
            
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Profile not found with id: " + id);
        }
    }

    // Get profiles by region
    public List<Profile> getByRegion(String region) {
        return profileRepository.findByRegion(region);
    }

    // Get profile by user
    public Profile getProfileByUser(User user) {
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getEmail()));
    }
}