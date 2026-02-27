package com.disaster.Disaster_Management.disaster.acknowledgment.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.Duration;

@Entity
@Table(name = "alert_acknowledgments")
public class AlertAcknowledgment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "alert_id", nullable = false)
    private Long alertId;
    
    @Column(name = "responder_id", nullable = false)
    private Long responderId;
    
    @Column(name = "acknowledged_at", nullable = false)
    private LocalDateTime acknowledgedAt;
    
    @Column(name = "response_time_seconds")
    private Long responseTimeSeconds;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    // Constructors
    public AlertAcknowledgment() {}
    
    // Lifecycle methods
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (acknowledgedAt == null) {
            acknowledgedAt = LocalDateTime.now();
        }
    }
    
    // Helper method to calculate response time
    public void calculateResponseTime(LocalDateTime broadcastTime) {
        if (broadcastTime != null && acknowledgedAt != null) {
            this.responseTimeSeconds = Duration.between(broadcastTime, acknowledgedAt).getSeconds();
        }
    }
    
    // ========== GETTERS & SETTERS ==========
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getAlertId() {
        return alertId;
    }
    
    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }
    
    public Long getResponderId() {
        return responderId;
    }
    
    public void setResponderId(Long responderId) {
        this.responderId = responderId;
    }
    
    public LocalDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }
    
    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
    }
    
    public Long getResponseTimeSeconds() {
        return responseTimeSeconds;
    }
    
    public void setResponseTimeSeconds(Long responseTimeSeconds) {
        this.responseTimeSeconds = responseTimeSeconds;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}