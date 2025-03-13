package com.example.QueueManagementSystem.models;

import com.example.QueueManagementSystem.helper.QueueStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String queueNumber;

    @Enumerated(EnumType.STRING)
    private QueueStatus status=QueueStatus.Waiting;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="operator_id",nullable = true)
    private ServiceOperator operator;

    public Queue(){
        this.queueNumber= UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        this.createdAt = LocalDateTime.now();
    }
}
