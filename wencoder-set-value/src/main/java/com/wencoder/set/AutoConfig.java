package com.wencoder.set;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动配置
 * <p>
 * Created by 王林 on 2021-09-16 16:45:39
 */
@Slf4j
@Aspect
@Configuration
@ConditionalOnWebApplication
public class AutoConfig implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AutoConfig.applicationContext = applicationContext;
    }

    protected static <T> T getBean(Class<T> cla) {
        return applicationContext.getBean(cla);
    }

    @Around("@annotation(com.wencoder.set.EnableSetValue)")
    public Object doSetValue(ProceedingJoinPoint joinPoint) throws Throwable {

        Object ret = joinPoint.proceed();

        if (ObjectUtils.isNotEmpty(ret)) {
            //processObject(ret);
        } else if (ret instanceof Collection && CollectionUtils.isNotEmpty((Collection<?>) ret)) {

        }

        return ret;
    }

    Map<String, SetValue> groupMap = new HashMap<>(16);
    Map<String, Map<Object, Object>> groupResultMap = new HashMap<>(16);

    private void processObject(Class<?> aClass, Object obj) {
        // 获取类头上分组配置
        SetValueConfig groupConfig = aClass.getAnnotation(SetValueConfig.class);
        // 判断有没有设置分组
        if (ObjectUtils.isNotEmpty(groupConfig)) {
            SetValue[] groups = groupConfig.value();
            // 遍历分组，获取信息
            for (SetValue setValue : groups) {
                //groupQueryDataHandle(groupMap, groupResultMap, fieldFill, voClass, col);
                Object bean = getBean(setValue.beanClass());
            }
        }

    }


}
