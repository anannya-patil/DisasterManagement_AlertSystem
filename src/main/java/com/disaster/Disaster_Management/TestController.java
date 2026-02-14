package com.disaster.Disaster_Management;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/simple-test")
    public String test() {
        return "Controller is working!";
    }
    
    @GetMapping("/simple-test-public")
    public String testPublic() {
        return "Public controller is working!";
    }
}