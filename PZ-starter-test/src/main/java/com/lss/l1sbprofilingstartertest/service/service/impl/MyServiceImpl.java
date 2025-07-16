package com.lss.l1sbprofilingstartertest.service.service.impl;



import anotation.Profiling;
import com.lss.l1sbprofilingstartertest.service.service.MyService;
import org.springframework.stereotype.Service;

@Service
@Profiling
public class MyServiceImpl implements MyService {

    @Override
    public void warmUp() {

    }

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
