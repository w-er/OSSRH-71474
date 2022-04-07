package com.wencoder.fill;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order
@Aspect
@Configuration
@ConditionalOnWebApplication
public class FillAutoConfiguration {

    @Around("@annotation(com.wencoder.fill.FillEnable)")
    public Object doFill(ProceedingJoinPoint pjp) throws Throwable {
        Object ret = pjp.proceed();
        try {
            FillUtil fillUtil = new FillUtil(fillSourceContext());
            fillUtil.fill(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 加载所有 FillService 注解的 FillSource
     */
    @Bean
    @ConditionalOnMissingBean
    public FillSourceContext fillSourceContext() {
        return new FillSourceContext();
    }

}
