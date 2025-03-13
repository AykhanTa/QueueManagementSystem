package com.example.QueueManagementSystem.controller;

import com.example.QueueManagementSystem.helper.QueueStatus;
import com.example.QueueManagementSystem.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queue")
@RequiredArgsConstructor
public class QueueController {
    private final QueueService queueService;

    @PostMapping
    public String addQueue() {
        return queueService.addQueue();
    }

    @GetMapping("status/{queueNumber}")
    public QueueStatus getQueueStatus(@PathVariable String queueNumber) {
        return queueService.getQueueStatus(queueNumber);
    }

    @PostMapping("callQueue")
    public void callQueue(@RequestParam Integer operatorId) {
         queueService.callNextQueue(operatorId);
    }

    @PostMapping("complete/{queueNumber}")
    public void completeQueue(@RequestParam String queueNumber) {
        queueService.completeQueue(queueNumber);
    }
}
