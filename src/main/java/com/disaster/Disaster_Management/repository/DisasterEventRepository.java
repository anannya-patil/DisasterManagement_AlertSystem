package com.disaster.Disaster_Management.repository;

import com.disaster.Disaster_Management.entity.DisasterEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DisasterEventRepository 
        extends JpaRepository<DisasterEvent, Long>,
                JpaSpecificationExecutor<DisasterEvent> {
}