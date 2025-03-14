package com.example.QueueManagementSystem.service;

import com.example.QueueManagementSystem.helper.QueueStatus;

public interface QueueService {
    String addQueue();
    QueueStatus getQueueStatus(String queueNumber);
    String callNextQueue(Integer operatorId,Boolean customerCame);
    void completeQueue(String queueNumber);
    void comeQueue(String queueNumber);
    void deleteExpiredQueues();
}
