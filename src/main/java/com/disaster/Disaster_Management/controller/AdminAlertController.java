package com.disaster.Disaster_Management.controller;

import com.disaster.Disaster_Management.disaster.alert.entity.Alert;
import com.disaster.Disaster_Management.disaster.alert.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/alerts")
public class AdminAlertController {

    private final AlertService alertService;

    public AdminAlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    // ==============================
    // POST: Broadcast Alert
    // ==============================
    @PostMapping
    public ResponseEntity<Alert> broadcastAlert(
            @RequestParam Long disasterId,
            @RequestParam String title,
            @RequestParam String message,
            @RequestParam String region) {

        Alert alert = alertService.broadcastAlert(disasterId, title, message, region);
        return ResponseEntity.ok(alert);
    }

    // ==============================
    // PUT: Resolve Alert
    // ==============================
    @PutMapping("/{id}/resolve")
    public ResponseEntity<Alert> resolveAlert(@PathVariable Long id) {

        Alert resolvedAlert = alertService.resolveAlert(id);
        return ResponseEntity.ok(resolvedAlert);
    }

    // ==============================
    // GET: All Alerts
    // ==============================
    @GetMapping
    public ResponseEntity<List<Alert>> getAllAlerts() {

        List<Alert> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(alerts);
    }
}