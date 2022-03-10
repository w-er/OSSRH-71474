package com.wencoder.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 自动配置
 * <p>
 * Created by 王林 on 2021-09-16 16:45:39
 */
@Slf4j
@Order
@Configuration
@ConditionalOnWebApplication
public class AutoConfig {

}
