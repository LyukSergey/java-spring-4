package com.lss.l1sbprofilingstarter.config;

import com.lss.l1sbprofilingstarter.beanPostProcessor.ProfilingBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ProfilingProperties.class)
@ConditionalOnProperty(prefix = "profiling", name = "enabled", havingValue = "true")
public class ProfilingAutoConfiguration {

    @Bean
    public ProfilingBeanPostProcessor profilingBeanPostProcessor() {
        return new ProfilingBeanPostProcessor();
    }
}
