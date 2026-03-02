package com.disaster.Disaster_Management.repository;

import com.disaster.Disaster_Management.entity.Role;
import com.disaster.Disaster_Management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(Role role);
}