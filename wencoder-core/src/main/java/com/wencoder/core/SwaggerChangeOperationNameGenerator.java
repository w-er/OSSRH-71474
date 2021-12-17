package com.wencoder.core;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * swagger 重复方法日志隐藏
 * <p>
 * Created by 王林 on 2021-11-25 14:38:18
 */
@Slf4j
@Aspect
public class SwaggerChangeOperationNameGenerator {

    protected final Map<String, Integer> generated = newHashMap();

    @Pointcut("execution(* springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator.startingWith(String))")
    public void c() {
    }

    @Around("c()")
    public Object a(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        return startingWith(String.valueOf(args[0]));
    }

    private String startingWith(String prefix) {
        if (generated.containsKey(prefix)) {
            generated.put(prefix, generated.get(prefix) + 1);
            return String.format("%s_%s", prefix, generated.get(prefix));
        } else {
            generated.put(prefix, 0);
            return prefix;
        }
    }
}

