package com.disaster.Disaster_Management.disaster.acknowledgment.service;

import com.disaster.Disaster_Management.disaster.alert.entity.Alert;
import com.disaster.Disaster_Management.disaster.alert.repository.AlertRepository;
import com.disaster.Disaster_Management.disaster.acknowledgment.entity.AlertAcknowledgment;
import com.disaster.Disaster_Management.disaster.acknowledgment.repository.AlertAcknowledgmentRepository;
import com.disaster.Disaster_Management.entity.User;
import com.disaster.Disaster_Management.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlertAcknowledgmentService {

    private final AlertAcknowledgmentRepository acknowledgmentRepository;
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public AlertAcknowledgmentService(
            AlertAcknowledgmentRepository acknowledgmentRepository,
            AlertRepository alertRepository,
            UserRepository userRepository) {
        this.acknowledgmentRepository = acknowledgmentRepository;
        this.alertRepository = alertRepository;
        this.userRepository = userRepository;
    }

    // =====================================
    // RESPONDER: Acknowledge Alert
    // =====================================
    public AlertAcknowledgment acknowledgeAlert(Long alertId) {

        if (alertId == null) {
            throw new IllegalArgumentException("Alert ID cannot be null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        String responderEmail;

        if (principal instanceof com.disaster.Disaster_Management.entity.User user) {
            responderEmail = user.getEmail();
        } else {
            responderEmail = principal.toString();
        }

        User responder = userRepository.findByEmail(responderEmail)
                .orElseThrow(() -> new RuntimeException("Responder not found"));

        if (acknowledgmentRepository.existsByAlertIdAndResponderId(alertId, responder.getId())) {
            throw new RuntimeException("Alert already acknowledged by this responder");
        }

        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        if (!"ACTIVE".equals(alert.getStatus())) {
            throw new RuntimeException("Cannot acknowledge non-active alert");
        }

        AlertAcknowledgment acknowledgment = new AlertAcknowledgment();
        acknowledgment.setAlertId(alertId);
        acknowledgment.setResponderId(responder.getId());
        acknowledgment.setAcknowledgedAt(LocalDateTime.now());

        acknowledgment.calculateResponseTime(alert.getBroadcastTime());

        return acknowledgmentRepository.save(acknowledgment);
    }

    // =====================================
    // RESPONDER: View Active Alerts
    // =====================================
    public List<Alert> getActiveAlertsByRegion(String region) {
        return alertRepository.findActiveAlertsByRegion(region);
    }

    // =====================================
    // RESPONDER: View Own Acknowledgments
    // =====================================
    public List<AlertAcknowledgment> getAcknowledgmentsForResponder() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object principal = authentication.getPrincipal();

        String responderEmail;

        if (principal instanceof com.disaster.Disaster_Management.entity.User user) {
            responderEmail = user.getEmail();
        } else {
            responderEmail = principal.toString();
        }

        User responder = userRepository.findByEmail(responderEmail)
                .orElseThrow(() -> new RuntimeException("Responder not found"));

        return acknowledgmentRepository.findByResponderId(responder.getId());
    }
}