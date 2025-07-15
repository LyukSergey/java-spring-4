package com.example.L1_sb_profiling_starter_test_vishchun.service.impl;

import com.example.L1_sb_profiling_starter_test_vishchun.service.TransactionalTestService;
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
