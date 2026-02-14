package com.disaster.Disaster_Management.service;

import com.disaster.Disaster_Management.entity.Profile;
import com.disaster.Disaster_Management.entity.User;
import com.disaster.Disaster_Management.repository.ProfileRepository;
import com.disaster.Disaster_Management.repository.UserRepository;
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
    public Profile createProfile(Profile profile) {

        // 1️⃣ Get logged-in User from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // ✅ directly get User

        // 2️⃣ Prevent duplicate profile
        if (profileRepository.existsByUser(user)) {
            throw new RuntimeException("Profile already exists for this user");
        }

        // 3️⃣ Link profile to user
        profile.setUser(user);

        // 4️⃣ Save profile
        return profileRepository.save(profile);
    }

    // Get all profiles (can restrict later by role)
    public List<Profile> getAll() {
        return profileRepository.findAll();
    }

    // Update profile (only owner should update ideally — advanced step)
    public Profile update(Long id, Profile updatedProfile) {

        Profile existing = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        existing.setFullName(updatedProfile.getFullName());
        existing.setPhone(updatedProfile.getPhone());
        existing.setRegion(updatedProfile.getRegion());

        return profileRepository.save(existing);
    }

    // Get profiles by region
    public List<Profile> getByRegion(String region) {
        return profileRepository.findByRegion(region);
    }
    // Add this method to ProfileService.java
public Profile getProfileByUser(User user) {
    return profileRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Profile not found for user: " + user.getEmail()));
}
}
