package com.disaster.Disaster_Management.service;

import com.disaster.Disaster_Management.entity.*;
import com.disaster.Disaster_Management.repository.DisasterEventRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DisasterEventService {

    private final DisasterEventRepository repository;

    public DisasterEventService(DisasterEventRepository repository) {
        this.repository = repository;
    }

    // 🔹 Public filtering with pagination
    public Page<DisasterEvent> getFilteredDisasters(
            DisasterType type,
            SeverityLevel severity,
            String location,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean liveOnly,
            Double minMagnitude,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("eventTime").descending());

        Specification<DisasterEvent> spec = Specification.where(null);

        if (type != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("disasterType"), type));
        }

        if (severity != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("severity"), severity));
        }

        if (location != null && !location.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("locationName")),
                            "%" + location.toLowerCase() + "%"));
        }

        if (startDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("eventTime"), startDate));
        }

        if (endDate != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("eventTime"), endDate));
        }

        if (Boolean.TRUE.equals(liveOnly)) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), DisasterStatus.VERIFIED));
        }

        if (minMagnitude != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("magnitude"), minMagnitude));
        }

        return repository.findAll(spec, pageable);
    }

    //Get Disaster by ID - UPDATED WITH DEBUG
    public Optional<DisasterEvent> getDisasterById(Long id) {
        System.out.println("Service: Looking for disaster with ID: " + id);
        
        Optional<DisasterEvent> result = repository.findById(id);
        
        if (result.isPresent()) {
            DisasterEvent event = result.get();
            System.out.println("   Service: Found disaster: " + event.getTitle());
            System.out.println("   Location: " + event.getLocationName());
            System.out.println("   Severity: " + event.getSeverity());
            System.out.println("   Type: " + event.getDisasterType());
        } else {
            System.out.println("Service: No disaster found with ID: " + id);
        }
        
        return result;
    }

    // 🔹 Admin Approve
    public DisasterEvent approve(Long id, String adminEmail) {
        DisasterEvent event = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        event.setStatus(DisasterStatus.VERIFIED);
        event.setApprovedBy(adminEmail);
        event.setApprovedAt(LocalDateTime.now());

        return repository.save(event);
    }

    // 🔹 Admin Reject
    public DisasterEvent reject(Long id) {
        DisasterEvent event = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        event.setStatus(DisasterStatus.REJECTED);
        return repository.save(event);
    }

    //Admin Resolve
    public DisasterEvent resolve(Long id) {
        DisasterEvent event = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        event.setStatus(DisasterStatus.RESOLVED);
        return repository.save(event);
    }

    //Get Pending (Admin)
    public Page<DisasterEvent> getPending(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return repository.findAll(
                (root, query, cb) ->
                        cb.equal(root.get("status"), DisasterStatus.PENDING),
                pageable
        );
    }

    //Update Disaster
    public DisasterEvent update(Long id, DisasterEvent updatedData) {

        DisasterEvent existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disaster not found"));

        existing.setTitle(updatedData.getTitle());
        existing.setDescription(updatedData.getDescription());
        existing.setDisasterType(updatedData.getDisasterType());
        existing.setSeverity(updatedData.getSeverity());
        existing.setLatitude(updatedData.getLatitude());
        existing.setLongitude(updatedData.getLongitude());
        existing.setLocationName(updatedData.getLocationName());
        existing.setSource(updatedData.getSource());
        existing.setMagnitude(updatedData.getMagnitude());
        existing.setEventTime(updatedData.getEventTime());

        return repository.save(existing);
    }

    //5-Record Retention Logic
    public void saveWithRetention(DisasterEvent event) {
        String location = event.getLocationName();
        
        // Count existing events for this location
        Specification<DisasterEvent> spec = (root, query, cb) -> 
            cb.equal(root.get("locationName"), location);
        
        long count = repository.count(spec);
        
        if (count >= 5) {
            // Find oldest event and delete it
            Pageable pageable = PageRequest.of(0, 1, Sort.by("createdAt").ascending());
            Page<DisasterEvent> oldest = repository.findAll(spec, pageable);
            if (oldest.hasContent()) {
                repository.delete(oldest.getContent().get(0));
                System.out.println("🗑️ Deleted oldest event for " + location);
            }
        }
        
        // Save new event
        event.setStatus(DisasterStatus.PENDING);
        repository.save(event);
        System.out.println("Saved new event for " + location);
    }

    // Auto-Verification for Critical Earthquakes
    @Scheduled(fixedRate = 60000) // Every minute
    public void autoVerifyCriticalEarthquakes() {
        System.out.println("Auto verification check running at: " + LocalDateTime.now());

        List<DisasterEvent> events = repository.findAutoVerifiableEarthquakes();

        System.out.println("Matching events count: " + events.size());

        events.forEach(event -> {
            event.setStatus(DisasterStatus.VERIFIED);
            repository.save(event);
            System.out.println("Auto-verified earthquake: " + event.getTitle() + " at " + event.getLocationName());
        });
    }
}