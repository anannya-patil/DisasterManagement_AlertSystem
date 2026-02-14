package com.disaster.Disaster_Management.repository;

import com.disaster.Disaster_Management.entity.Profile;
import com.disaster.Disaster_Management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> findByRegion(String region);
    boolean existsByUser(User user);
    Optional<Profile> findByUser(User user);  // ✅ NEW METHOD
}