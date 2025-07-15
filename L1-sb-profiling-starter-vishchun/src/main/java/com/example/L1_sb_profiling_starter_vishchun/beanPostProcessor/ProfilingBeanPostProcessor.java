package com.example.L1_sb_profiling_starter_vishchun.beanPostProcessor;

import com.example.L1_sb_profiling_starter_vishchun.anotation.Profiling;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

public class ProfilingBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class<?>> beansToProfile = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Profiling.class)) {
            System.out.println("Додаю до мапи : " + beanName + " класу " + bean.getClass().getName());
            beansToProfile.put(beanName, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (beansToProfile.containsKey(beanName)) {
            System.out.println("Створюю проксі для біну " + beanName);
            Class<?> beanClass = beansToProfile.get(beanName);
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        long before = System.nanoTime();
                        try {
                            Object result = method.invoke(bean, args);
                            long after = System.nanoTime();
                            System.out.println(
                                    "Профілювання: метод " + method.getName() + " виконав свою роботу за " + (after - before) + " нс");
                            return result;
                        } catch (java.lang.reflect.InvocationTargetException e) {
                            throw e.getTargetException();
                        }
                    }
            );
        }
        return bean;
    }
}