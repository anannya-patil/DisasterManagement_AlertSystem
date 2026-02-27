package com.disaster.Disaster_Management.disaster.acknowledgment.repository;

import com.disaster.Disaster_Management.disaster.acknowledgment.entity.AlertAcknowledgment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertAcknowledgmentRepository extends JpaRepository<AlertAcknowledgment, Long> {
    
    // Find all acknowledgments for a specific alert
    List<AlertAcknowledgment> findByAlertId(Long alertId);
    
    // Find acknowledgment by a specific responder for an alert
    Optional<AlertAcknowledgment> findByAlertIdAndResponderId(Long alertId, Long responderId);
    
    // Find all acknowledgments by a responder
    List<AlertAcknowledgment> findByResponderId(Long responderId);
    
    // Check if a responder has acknowledged a specific alert
    boolean existsByAlertIdAndResponderId(Long alertId, Long responderId);
    
    // Get average response time for a specific alert
    @Query("SELECT AVG(a.responseTimeSeconds) FROM AlertAcknowledgment a WHERE a.alertId = :alertId")
    Double getAverageResponseTimeForAlert(@Param("alertId") Long alertId);
    
    // Count acknowledgments for an alert
    @Query("SELECT COUNT(a) FROM AlertAcknowledgment a WHERE a.alertId = :alertId")
    long countAcknowledgmentsByAlertId(@Param("alertId") Long alertId);
}