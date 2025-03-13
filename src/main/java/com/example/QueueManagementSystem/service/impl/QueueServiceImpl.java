package com.example.QueueManagementSystem.service.impl;

import com.example.QueueManagementSystem.helper.QueueStatus;
import com.example.QueueManagementSystem.models.Queue;
import com.example.QueueManagementSystem.models.ServiceOperator;
import com.example.QueueManagementSystem.repository.QueueRepository;
import com.example.QueueManagementSystem.repository.ServiceOperatorRepository;
import com.example.QueueManagementSystem.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private final QueueRepository queueRepository;
    private final ServiceOperatorRepository serviceOperatorRepository;
    private final java.util.Queue<Queue> queue=new LinkedList<>();

    @Override
    public String addQueue() {
        Queue newQueue = new Queue();
        queueRepository.save(newQueue);
        queue.offer(newQueue);
        return newQueue.getQueueNumber();
    }

    @Override
    public QueueStatus getQueueStatus(String queueNumber) {
        Queue existQueue = queueRepository.findByQueueNumber(queueNumber)
                .orElseThrow(() -> new IllegalArgumentException("Queue can't find"));
        return existQueue.getStatus();
    }

    @Override
    public void callNextQueue(Integer operatorId) {
        if(queue.isEmpty()){
            throw new IllegalArgumentException("Queue can't be empty");
        }
        ServiceOperator operator=serviceOperatorRepository.findById(operatorId)
                .orElseThrow(()->new IllegalArgumentException("Operator can't find"));

        Queue nextQueue = queue.poll();
        nextQueue.setStatus(QueueStatus.In_Service);
        nextQueue.setUpdatedAt(LocalDateTime.now());
        nextQueue.setOperator(operator);
        queueRepository.save(nextQueue);
    }

    @Override
    public void completeQueue(String queueNumber) {
        Queue existQueue = queueRepository.findByQueueNumber(queueNumber)
                .orElseThrow(() -> new IllegalArgumentException("Queue can't find"));
        existQueue.setStatus(QueueStatus.Completed);
        existQueue.setUpdatedAt(LocalDateTime.now());
        queueRepository.save(existQueue);
    }
}
