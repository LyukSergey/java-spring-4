package com.example.starter2.service;


public interface MyService {
    /**
     * Метод для демонстрації роботи профілювання
     */
    void doWork();

    /**
     * Метод для більш тривалої обробки
     */
    String processText(String text, int repetitions);
}
