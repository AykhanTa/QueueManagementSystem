package com.example.QueueManagementSystem.service.impl;

import com.example.QueueManagementSystem.helper.QueueStatus;
import com.example.QueueManagementSystem.models.Queue;
import com.example.QueueManagementSystem.models.ServiceOperator;
import com.example.QueueManagementSystem.repository.QueueRepository;
import com.example.QueueManagementSystem.repository.ServiceOperatorRepository;
import com.example.QueueManagementSystem.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {
    private final QueueRepository queueRepository;
    private final ServiceOperatorRepository serviceOperatorRepository;
    private final java.util.Queue<Queue> queue = new LinkedList<>();

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
    public String callNextQueue(Integer operatorId, Boolean customerCame) {
        if (queue.isEmpty()) {
            throw new IllegalArgumentException("Queue is empty");
        }

        ServiceOperator operator = serviceOperatorRepository.findById(operatorId)
                .orElseThrow(() -> new IllegalArgumentException("Operator not found"));

        Queue nextQueue = queue.poll();
        nextQueue.setUpdatedAt(LocalDateTime.now());

        if (customerCame) {
            nextQueue.setStatus(QueueStatus.In_Service);
            nextQueue.setOperator(operator);
            queueRepository.save(nextQueue);
            return "Customer arrived, queue is now IN_SERVICE";
        } else {
            nextQueue.setStatus(QueueStatus.Waiting);
            nextQueue.setOperator(operator);
            queueRepository.save(nextQueue);
            return "Customer did not arrive, waiting 15 minutes";
        }
    }



    @Override
    public void completeQueue(String queueNumber) {
        Queue existQueue = queueRepository.findByQueueNumber(queueNumber)
                .orElseThrow(() -> new IllegalArgumentException("Queue can't find"));
        existQueue.setStatus(QueueStatus.Completed);
        existQueue.setUpdatedAt(LocalDateTime.now());
        queueRepository.save(existQueue);
    }

    @Override
    public void comeQueue(String queueNumber) {
        Queue existQueue = queueRepository.findByQueueNumber(queueNumber)
                .orElseThrow(() -> new IllegalArgumentException("Queue can't find"));
        if(existQueue.getStatus().equals(QueueStatus.Expired)) {
            throw new IllegalArgumentException("Queue is expired");
        }
        existQueue.setStatus(QueueStatus.In_Service);
        existQueue.setUpdatedAt(LocalDateTime.now());
        queueRepository.save(existQueue);
    }

    @Override
    public void deleteExpiredQueues() {
        List<Queue> queues = queueRepository.findQueuesByStatus(QueueStatus.Expired);
        queueRepository.deleteAll(queues);
    }

    @Scheduled(fixedRate = 60000)
    public void checkExpiredQueues() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threshold = now.minusMinutes(2);


        List<Queue> expiredQueues = queueRepository.findByStatusAndUpdatedAtBefore(QueueStatus.Waiting, threshold);

        for (Queue queue : expiredQueues) {
            queue.setStatus(QueueStatus.Expired);
            queue.setUpdatedAt(LocalDateTime.now());
        }
        queueRepository.saveAll(expiredQueues);
        System.out.println("Expired queues cleared: " + expiredQueues.size());
    }


}
