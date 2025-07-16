package com.example.fleet;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringLiquibaseDependsOnPostProcessor {

    @Bean
    public static BeanFactoryPostProcessor liquibaseDependsOnSchemaInit() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
                if (beanFactory.containsBeanDefinition("liquibase")) {
                    beanFactory.getBeanDefinition("liquibase")
                        .setDependsOn("schemaInitBean");
                }
            }
        };
    }
}
