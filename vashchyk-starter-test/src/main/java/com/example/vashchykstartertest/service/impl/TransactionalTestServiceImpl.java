package com.example.vashchykstartertest.service.impl;

import com.example.vashchykstartertest.service.TransactionalTestService;
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
