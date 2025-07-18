package com.example.starter1.config;

import com.example.starter1.beanPostProcessor.ProfilingBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@EnableConfigurationProperties(ProfilingProperties.class)
@ConditionalOnProperty(prefix = "profiling", name = "enabled", havingValue = "true")
public class ProfilingAutoConfiguration {

    @Bean
    public ProfilingBeanPostProcessor profilingBeanPostProcessor(ProfilingProperties properties) {
        return new ProfilingBeanPostProcessor(properties);
    }
}