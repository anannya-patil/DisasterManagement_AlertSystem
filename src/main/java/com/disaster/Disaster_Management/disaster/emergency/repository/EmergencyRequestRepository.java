package com.disaster.Disaster_Management.disaster.emergency.repository;

import com.disaster.Disaster_Management.disaster.emergency.entity.EmergencyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest, Long> {
    
    // Find requests by citizen
    List<EmergencyRequest> findByCitizenId(Long citizenId);
    
    // Find requests by status
    List<EmergencyRequest> findByStatus(String status);
    
    // Find pending requests in a region
    @Query("SELECT e FROM EmergencyRequest e WHERE e.region = :region AND e.status = 'PENDING'")
    List<EmergencyRequest> findPendingRequestsByRegion(@Param("region") String region);
    
    // Find requests assigned to a specific responder
    List<EmergencyRequest> findByAssignedResponderId(Long responderId);
    
    // Find active requests (PENDING or ASSIGNED) in a region
    @Query("SELECT e FROM EmergencyRequest e WHERE e.region = :region AND e.status IN ('PENDING', 'ASSIGNED')")
    List<EmergencyRequest> findActiveRequestsByRegion(@Param("region") String region);
    
    // Count pending requests
    @Query("SELECT COUNT(e) FROM EmergencyRequest e WHERE e.status = 'PENDING'")
    long countPendingRequests();
    
    // Find nearest pending request (simplified - actual implementation would use spatial queries)
    @Query(value = "SELECT * FROM emergency_requests e WHERE e.status = 'PENDING' ORDER BY " +
           "POW((e.latitude - :lat), 2) + POW((e.longitude - :lng), 2) LIMIT 1", 
           nativeQuery = true)
    EmergencyRequest findNearestPendingRequest(@Param("lat") Double latitude, @Param("lng") Double longitude);
}