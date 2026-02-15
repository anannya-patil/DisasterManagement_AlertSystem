package com.disaster.Disaster_Management.controller;

import com.disaster.Disaster_Management.entity.Profile;
import com.disaster.Disaster_Management.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Profile profile) {
        return profileService.createProfile(profile);
    }

    @GetMapping
    public List<Profile> getAll() {
        return profileService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Profile profile) {
        return profileService.update(id, profile);
    }

    @GetMapping("/region/{region}")
    public List<Profile> getByRegion(@PathVariable String region) {
        return profileService.getByRegion(region);
    }
}