package com.example.cloudnative.repository;

import com.example.cloudnative.domain.CloudUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CloudUser, Long> {
}
