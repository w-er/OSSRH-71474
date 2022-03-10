package com.wencoder.set;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设定值 - 标记需要设置的字段
 *
 * @author Wanglin
 * @since 2020-08-19 16:27:03
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SetValue {

    /**
     * 分组
     */
    String group() default "";

    /**
     * 需要调用的方法所在类
     */
    Class beanClass() default Object.class;

    /**
     * 方法的传参
     */
    String param() default "";

    /**
     * 单个查询的方法名
     * @return
     */
    String method() default "";

    /**
     * 批量查询的方法名（推荐）
     */
    String methodBatch() default "";

    /**
     * 调用方法后，需要从返回值取得的字段
     */
    String targetField() default "";

    /**
     * 与 group 配合
     */
    String targetFieldKey() default "";
}
