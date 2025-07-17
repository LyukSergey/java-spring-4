package com.example.starter2.service.impl;

import com.example.starter2.service.TransactionalTestService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionalTestServiceImpl implements TransactionalTestService {

    @Override
    @Transactional
    public void transactionalTestMethod() {
        System.out.println("Transaction method executed.");
    }
}
