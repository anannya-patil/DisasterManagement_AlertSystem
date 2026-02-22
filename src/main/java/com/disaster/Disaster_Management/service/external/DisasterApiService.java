package com.disaster.Disaster_Management.service.external;

import com.disaster.Disaster_Management.entity.DisasterEvent;
import com.disaster.Disaster_Management.service.DisasterEventService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class DisasterApiService {

    private final WeatherApiService weatherApiService;
    private final EarthquakeApiService earthquakeApiService;
    private final DisasterEventService disasterEventService;

    public DisasterApiService(
            WeatherApiService weatherApiService,
            EarthquakeApiService earthquakeApiService,
            DisasterEventService disasterEventService) {
        this.weatherApiService = weatherApiService;
        this.earthquakeApiService = earthquakeApiService;
        this.disasterEventService = disasterEventService;
    }

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void syncAllDisasters() {
        System.out.println("🔄 Syncing disasters from external APIs...");
        
        List<DisasterEvent> allEvents = new ArrayList<>();
        
        allEvents.addAll(weatherApiService.fetchWeatherAlerts());
        allEvents.addAll(earthquakeApiService.fetchEarthquakes());
        
        for (DisasterEvent event : allEvents) {
            disasterEventService.saveWithRetention(event);
        }
        
        System.out.println("✅ Synced " + allEvents.size() + " disasters");
    }
}