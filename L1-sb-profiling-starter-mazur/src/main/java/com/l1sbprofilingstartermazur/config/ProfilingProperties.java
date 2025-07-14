package com.l1sbprofilingstartermazur.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "profiling")
public class ProfilingProperties {
    private boolean enabled = false;
}
