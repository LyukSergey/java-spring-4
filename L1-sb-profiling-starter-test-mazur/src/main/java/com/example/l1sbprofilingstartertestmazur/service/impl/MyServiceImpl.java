package com.example.l1sbprofilingstartertestmazur.service.impl;

import com.example.l1sbprofilingstartertestmazur.service.MyService;
import com.l1sbprofilingstartermazur.anotation.Profiling;
import org.springframework.stereotype.Service;

@Service
@Profiling // Позначаємо, що цей бін потрібно профілювати
public class MyServiceImpl implements MyService {
    @Override
    public void doWork() {
        System.out.println("Виконую важливу роботу...");
        try {
            Thread.sleep(100); // Імітуємо роботу
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
