package com.wencoder.security.annotation;

import com.wencoder.security.SecurityAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自定义权限认证启动注解
 * <p>
 * Created by 王林 on 2021-09-16 16:08:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SecurityAutoConfig.class})
public @interface EnableWCSecurity {

}
