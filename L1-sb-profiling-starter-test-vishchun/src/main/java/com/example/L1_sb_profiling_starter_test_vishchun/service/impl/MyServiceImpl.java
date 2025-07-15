package com.example.L1_sb_profiling_starter_test_vishchun.service.impl;

import com.example.L1_sb_profiling_starter_test_vishchun.anotation.Profiling;
import com.example.L1_sb_profiling_starter_test_vishchun.service.MyService;
import com.example.L1_sb_profiling_starter_test_vishchun.service.TransactionalTestService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Profiling
@RequiredArgsConstructor
public class MyServiceImpl implements MyService {

    private final TransactionalTestService transactionalTestService;

    @Override
    @PostConstruct
    public void warmUp() {
        System.out.println("Починаю розігрів...");
        try {
            Thread.sleep(100); // Імітуємо розігрів 100мс = 0,1c
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Розігрів завершено.");
    }

    @Override
    public void doWork() {
        System.out.println("Виконую важливу роботу...");
        transactionalTestService.transactionalTestMethod();
        try {
            Thread.sleep(100); // Імітуємо роботу 100мс = 0,1c
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
