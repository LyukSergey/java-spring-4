package com.lss.l1sbprofilingstartertest.service.impl;

import com.lss.l1sbprofilingstartertest.service.TransactionalTestService;
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
