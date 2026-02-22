package com.disaster.Disaster_Management.service.external;

import com.disaster.Disaster_Management.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherApiService {

    private final WebClient webClient;

    public WeatherApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5")
                .build();
    }

    public List<DisasterEvent> fetchWeatherAlerts() {
        List<DisasterEvent> events = new ArrayList<>();
        
        DisasterEvent event = new DisasterEvent();
        event.setTitle("Heavy Rainfall Warning");
        event.setDescription("Expected heavy rainfall in next 24 hours");
        event.setDisasterType(DisasterType.FLOOD);
        event.setSeverity(SeverityLevel.MEDIUM);
        event.setLocationName("Hyderabad");
        event.setLatitude(17.3850);
        event.setLongitude(78.4867);
        event.setSource("OpenWeather");
        event.setEventTime(LocalDateTime.now());
        event.setStatus(DisasterStatus.PENDING);
        
        events.add(event);
        return events;
    }
}