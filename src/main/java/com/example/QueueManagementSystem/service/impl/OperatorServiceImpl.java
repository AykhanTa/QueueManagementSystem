package com.example.QueueManagementSystem.service.impl;

import com.example.QueueManagementSystem.repository.ServiceOperatorRepository;
import com.example.QueueManagementSystem.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperatorServiceImpl implements OperatorService {
    private final ServiceOperatorRepository serviceOperatorRepository;

}
