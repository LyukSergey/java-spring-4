package com.example.starter1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "profiling")
public class ProfilingProperties {

    private boolean enabled = false; // За замовчуванням вимкнено
    private boolean verbose = true; // За замовчуванням детальний вивід
    private long thresholdNanos = 0; // Поріг для вивода в нс (0 - виводити все)

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public long getThresholdNanos() {
        return thresholdNanos;
    }

    public void setThresholdNanos(long thresholdNanos) {
        this.thresholdNanos = thresholdNanos;
    }
}
