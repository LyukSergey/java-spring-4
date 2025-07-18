package com.example.starter2.service.impl;

import com.example.starter1.anotation.Profiling;
import com.example.starter2.service.MyService;
import org.springframework.stereotype.Service;


@Service
@Profiling
public class MyServiceImpl implements MyService {

    @Override
    public void doWork() {
        System.out.println("Виконую важливу роботу...");
        try {
            // Імітуємо роботу
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Роботу завершено.");
    }

    @Override
    public String processText(String text, int repetitions) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < repetitions; i++) {
            result.append(text);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return result.toString();
    }
}
