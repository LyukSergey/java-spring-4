package com.lss.l1sbprofilingstartertest.service.impl;

import com.lss.l1sbprofilingstarter.anotation.Profiling;
import com.lss.l1sbprofilingstartertest.service.MyService;
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
