package com.example.QueueManagementSystem.repository;

import com.example.QueueManagementSystem.helper.QueueStatus;
import com.example.QueueManagementSystem.models.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueueRepository extends JpaRepository<Queue, Integer> {
    Optional<Queue> findByQueueNumber(String queueNumber);
    List<Queue> findByStatusAndUpdatedAtBefore(QueueStatus status, LocalDateTime updatedAt);
    List<Queue> findQueuesByStatus(QueueStatus status);
}
