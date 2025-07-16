package com.lss.pzstartertest.service.impl;

import com.lss.pzstarter.anotation.Profiling;
import com.lss.pzstartertest.service.MyService;
import org.springframework.stereotype.Service;

@Service
@Profiling
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
