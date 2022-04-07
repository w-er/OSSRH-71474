package com.wencoder.fill;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FillTarget {

    // 别名
    String value() default "";

    // 指定获取源对象的字段
    String sourceField() default "";

}
