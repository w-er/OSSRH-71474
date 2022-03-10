package com.wencoder.set;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SetValueConfig {

    SetValue[] value();

}
