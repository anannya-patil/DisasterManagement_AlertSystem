package com.disaster.Disaster_Management.disaster.alert.service;

import com.disaster.Disaster_Management.disaster.alert.entity.Alert;
import com.disaster.Disaster_Management.disaster.alert.repository.AlertRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    // ==============================
    // ADMIN: Broadcast Alert
    // ==============================
    public Alert broadcastAlert(Long disasterId, String title, String message, String region) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        String adminEmail;
        if (principal instanceof com.disaster.Disaster_Management.entity.User user) {
            adminEmail = user.getEmail();
        } else {
            adminEmail = principal.toString();
        }

        Alert alert = new Alert();
        alert.setDisasterId(disasterId);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setRegion(region);
        alert.setBroadcastTime(LocalDateTime.now());
        alert.setStatus("ACTIVE");
        alert.setCreatedBy(adminEmail);

        return alertRepository.save(alert);
    }

    // ==============================
    // ADMIN: Resolve Alert
    // ==============================
    public Alert resolveAlert(Long alertId) {

        if (alertId == null) {
            throw new IllegalArgumentException("Alert ID cannot be null");
        }

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        if (!"ACTIVE".equals(alert.getStatus())) {
            throw new RuntimeException("Only ACTIVE alerts can be resolved");
        }

        alert.setStatus("RESOLVED");

        return alertRepository.save(alert);
    }

    // ==============================
    // PUBLIC: Get Active Alerts by Region
    // ==============================
    public List<Alert> getActiveAlertsByRegion(String region) {
        return alertRepository.findActiveAlertsByRegion(region);
    }

    // ==============================
    // ADMIN: Get All Alerts
    // ==============================
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}