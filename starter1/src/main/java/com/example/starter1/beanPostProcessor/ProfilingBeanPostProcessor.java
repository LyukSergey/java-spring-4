package com.example.starter1.beanPostProcessor;

import com.example.starter1.anotation.Profiling;
import com.example.starter1.config.ProfilingProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class<?>> beansToProfile = new HashMap<>();
    private final ProfilingProperties properties;

    public ProfilingBeanPostProcessor(ProfilingProperties properties) {
        this.properties = properties;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Profiling.class)) {
            System.out.println("Додаю до мапи : " + beanName + " класу " + bean.getClass().getName());
            beansToProfile.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beansToProfile.containsKey(beanName)) {
            System.out.println("Створюю проксі для біну " + beanName);
            Class<?> beanClass = beansToProfile.get(beanName);
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        long before = System.nanoTime();
                        Object result = method.invoke(bean, args);
                        long after = System.nanoTime();
                        long executionTime = after - before;

                        // Виводимо інформацію, лише якщо час виконання перевищує поріг
                        if (executionTime >= properties.getThresholdNanos()) {
                            if (properties.isVerbose()) {
                                System.out.println(
                                    "Профілювання: метод " + method.getName() + 
                                    " класу " + beanClass.getSimpleName() + 
                                    " виконаний за " + executionTime + " нс" +
                                    " (" + (executionTime / 1_000_000.0) + " мс)");
                            } else {
                                System.out.println(
                                    "Профілювання: метод " + method.getName() + 
                                    " виконаний за " + executionTime + " нс");
                            }
                        }
                        return result;
                    }
            );
        }
        return bean;
    }
}