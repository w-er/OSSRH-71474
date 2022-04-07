package com.wencoder.fill;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FillSource {

    // 源名称
    String value() default "";

    // 指定存储源对象的 Map key filed
    String sourceKey() default "";

    // 对多组查询取别名，对应 FillParam，FillTarget
    String[] alias() default "";

}
