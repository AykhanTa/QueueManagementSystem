package com.example.QueueManagementSystem.repository;

import com.example.QueueManagementSystem.models.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueRepository extends JpaRepository<Queue, Integer> {
    Optional<Queue> findByQueueNumber(String queueNumber);
}
