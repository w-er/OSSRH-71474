package com.wencoder.fill;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 获取Spring容器中，定义的源
 */
public class FillSourceContext implements ApplicationListener<ContextRefreshedEvent> {

    public static Map<String, Invoker> SOURCE_MAP = new ConcurrentHashMap<>(16);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器
        if (event.getApplicationContext().getParent() == null) {
            List<Invoker> invokerList = new ArrayList<>();
            ApplicationContext applicationContext = event.getApplicationContext();
            Map<String, Object> beans = applicationContext.getBeansWithAnnotation(FillService.class);
            for (Object bean : beans.values()) {
                Method[] methods = bean.getClass().getMethods();
                List<Invoker> beanSourceList = Arrays.stream(methods)
                        .filter(method -> AnnotationUtils.findAnnotation(method, FillSource.class) != null)
                        .map(method -> {
                            FillSource source = AnnotationUtils.findAnnotation(method, FillSource.class);
                            return new Invoker(method, source.value(), source.sourceKey(), bean);
                        }).collect(Collectors.toList());
                invokerList.addAll(beanSourceList);
            }
            SOURCE_MAP = invokerList.stream().collect(Collectors.toMap(Invoker::getName, x -> x));
        }
    }

    /**
     * 根据源名称获取源方法
     *
     * @param name 源名称
     * @return Invoker
     */
    public Invoker getInvoker(String name) {
        return SOURCE_MAP.get(name);
    }

    /**
     * 根据源名称获取源方法，并执行方法
     *
     * @param name   源名称
     * @param params 参数
     * @return 结果
     */
    public Object invoker(String name, Object... params) {
        Invoker invoker = SOURCE_MAP.get(name);
        if (invoker == null) {
            return null;
        }
        try {
            return invoker.getMethod().invoke(invoker.getBean(), params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    @ToString
    public static class Invoker {
        // 方法
        private Method method;
        // 源名称
        private String name;
        // 源KEY
        private String sourceKey;
        // 源对象
        private Object bean;

        public Invoker(Method method, String name, String sourceKey, Object bean) {
            this.method = method;
            this.name = name;
            this.sourceKey = sourceKey;
            this.bean = bean;
        }
    }
}