package com.wencoder.security;

import com.wencoder.security.cfg.DefaultSecurityConfig;
import com.wencoder.security.domain.DefaultUserDetails;
import com.wencoder.security.filter.DefaultSecurityFilter;
import com.wencoder.security.handler.DefaultAuthenticationEntryPoint;
import com.wencoder.security.properties.SecurityProperties;
import com.wencoder.security.service.DefaultUserDetailsService;
import com.wencoder.security.service.DefaultUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

/**
 * 权限自动配置
 * <p>
 * Created by 王林 on 2021-09-16 16:45:39
 */
@Slf4j
@Order
@Configuration
@ConditionalOnWebApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({SecurityProperties.class})
public class SecurityAutoConfig {

    /**
     * 创建默认密码加密
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 默认用户实现
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultUserDetailsService defaultUserDetailsService() {
        DefaultUserDetails user = new DefaultUserDetails()
                .setId(1).setUsername("test")
                .setPassword("123456")
                .setRoles(Collections.singletonList("Test"));
        log.info("权限认证模块默认账号：{}", user.toString());
        return new DefaultUserDetailsServiceImpl(user);
    }

    /**
     * 默认错误处理
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAuthenticationEntryPoint authenticationEntryPoint() {
        return new DefaultAuthenticationEntryPoint();
    }

    /**
     * 默认请求处理
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultSecurityFilter defaultSecurityFilter(DefaultUserDetailsService service,
                                                       DefaultAuthenticationEntryPoint point,
                                                       SecurityProperties properties) {
        return new DefaultSecurityFilter(service, point, properties);
    }

    /**
     * 默认权限配置
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultSecurityConfig defaultSecurityConfig(DefaultAuthenticationEntryPoint point,
                                                       DefaultSecurityFilter filter,
                                                       SecurityProperties properties) {
        return new DefaultSecurityConfig(point, filter, properties);
    }

}
