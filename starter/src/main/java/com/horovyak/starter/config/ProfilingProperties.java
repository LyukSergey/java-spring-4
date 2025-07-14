package com.horovyak.starter.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "profiling")
public class ProfilingProperties {
    private boolean enabled = false; // За замовчуванням вимкнено
    // getters and setters
}
