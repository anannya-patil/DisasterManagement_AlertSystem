package com.disaster.Disaster_Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // ✅ Already present
public class DisasterManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(DisasterManagementApplication.class, args);
    }
}