package com.disaster.Disaster_Management.service.external;

import com.disaster.Disaster_Management.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class EarthquakeApiService {

    private final WebClient webClient;

    public EarthquakeApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary")
                .build();
    }

    public List<DisasterEvent> fetchEarthquakes() {
        List<DisasterEvent> events = new ArrayList<>();
        
        DisasterEvent event = new DisasterEvent();
        event.setTitle("Earthquake in San Francisco");
        event.setDescription("Magnitude 4.5 earthquake detected");
        event.setDisasterType(DisasterType.EARTHQUAKE);
        event.setMagnitude(4.5);
        event.setSeverity(determineSeverity(4.5));
        event.setLocationName("San Francisco");
        event.setLatitude(37.7749);
        event.setLongitude(-122.4194);
        event.setSource("USGS");
        event.setEventTime(LocalDateTime.now());
        event.setStatus(DisasterStatus.PENDING);
        
        events.add(event);
        return events;
    }

    private SeverityLevel determineSeverity(double magnitude) {
        if (magnitude < 4) return SeverityLevel.LOW;
        if (magnitude < 6) return SeverityLevel.MEDIUM;
        if (magnitude < 7) return SeverityLevel.HIGH;
        return SeverityLevel.CRITICAL;
    }
}