package com.example.demostarterapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "profiling")
public class ProfilingProperties {

    private boolean enabled = false; // За замовчуванням вимкнено

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}
