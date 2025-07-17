package beanPostProcessor;

import anotation.Profiling;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ProfilingBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class<?>> beansToProfile = new HashMap<>();

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
                        System.out.println(
                                "Профілювання: метод " + method.getName() + " виконав свою роботу за " + (after - before) + " нс");
                        return result;
                    }
            );
        }
        return bean;
    }
}