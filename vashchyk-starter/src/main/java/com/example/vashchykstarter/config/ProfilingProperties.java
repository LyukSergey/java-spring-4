package com.example.vashchykstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "profiling")
public class ProfilingProperties {
    private boolean enabled = false; // За замовчуванням вимкнено
    // ! ми не використовуємо Lombok, тому обовязково треба прописати getters and setters
}