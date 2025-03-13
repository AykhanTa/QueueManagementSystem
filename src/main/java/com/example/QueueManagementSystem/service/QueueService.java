package com.example.QueueManagementSystem.service;

import com.example.QueueManagementSystem.helper.QueueStatus;
import com.example.QueueManagementSystem.models.Queue;

public interface QueueService {
    String addQueue();
    QueueStatus getQueueStatus(String queueNumber);
    void callNextQueue(Integer operatorId);
    void completeQueue(String queueNumber);
}
