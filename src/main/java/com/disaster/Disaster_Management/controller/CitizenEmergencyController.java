package com.disaster.Disaster_Management.controller;

import com.disaster.Disaster_Management.disaster.emergency.entity.EmergencyRequest;
import com.disaster.Disaster_Management.disaster.emergency.service.EmergencyRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citizen")
public class CitizenEmergencyController {

    private final EmergencyRequestService emergencyService;

    public CitizenEmergencyController(EmergencyRequestService emergencyService) {
        this.emergencyService = emergencyService;
    }

    // =====================================
    // POST: Submit Emergency Request
    // =====================================
    @PostMapping("/emergency-request")
    public ResponseEntity<EmergencyRequest> submitEmergencyRequest(
            @RequestParam String description,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String region) {

        EmergencyRequest request = emergencyService.createEmergencyRequest(
                description, latitude, longitude, region);

        return ResponseEntity.ok(request);
    }
}