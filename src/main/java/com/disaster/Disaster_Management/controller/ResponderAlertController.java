package com.disaster.Disaster_Management.controller;

import com.disaster.Disaster_Management.disaster.alert.entity.Alert;
import com.disaster.Disaster_Management.disaster.acknowledgment.entity.AlertAcknowledgment;
import com.disaster.Disaster_Management.disaster.acknowledgment.service.AlertAcknowledgmentService;
import com.disaster.Disaster_Management.entity.User;
import com.disaster.Disaster_Management.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responder/alerts")
public class ResponderAlertController {

    private final AlertAcknowledgmentService acknowledgmentService;
    private final UserRepository userRepository;

    public ResponderAlertController(AlertAcknowledgmentService acknowledgmentService,
                                     UserRepository userRepository) {
        this.acknowledgmentService = acknowledgmentService;
        this.userRepository = userRepository;
    }

    // =====================================
    // GET: View Active Alerts (By Region)
    // =====================================
    @GetMapping
    public ResponseEntity<List<Alert>> getActiveAlerts(Authentication authentication) {

        Object principal = authentication.getPrincipal();

        String responderEmail;

        if (principal instanceof com.disaster.Disaster_Management.entity.User user) {
            responderEmail = user.getEmail();
        } else {
            responderEmail = principal.toString();
        }

        User responder = userRepository.findByEmail(responderEmail)
                .orElseThrow(() -> new RuntimeException("Responder not found"));

        String region = responder.getProfile().getRegion();

        List<Alert> alerts = acknowledgmentService.getActiveAlertsByRegion(region);

        return ResponseEntity.ok(alerts);
    }

    // =====================================
    // POST: Acknowledge Alert
    // =====================================
    @PostMapping("/{id}/acknowledge")
    public ResponseEntity<AlertAcknowledgment> acknowledgeAlert(@PathVariable Long id) {

        AlertAcknowledgment acknowledgment = acknowledgmentService.acknowledgeAlert(id);
        return ResponseEntity.ok(acknowledgment);
    }
}