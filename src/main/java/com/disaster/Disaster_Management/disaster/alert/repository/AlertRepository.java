package com.disaster.Disaster_Management.disaster.alert.repository;

import com.disaster.Disaster_Management.disaster.alert.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    
    // Find alerts by region
    List<Alert> findByRegion(String region);
    
    // Find alerts by status
    List<Alert> findByStatus(String status);
    
    // Find active alerts in a specific region
    @Query("SELECT a FROM Alert a WHERE a.region = :region AND a.status = 'ACTIVE'")
    List<Alert> findActiveAlertsByRegion(@Param("region") String region);
    
    // Find alerts created by a specific admin
    List<Alert> findByCreatedBy(String createdBy);
    
    // Count active alerts
    @Query("SELECT COUNT(a) FROM Alert a WHERE a.status = 'ACTIVE'")
    long countActiveAlerts();
}