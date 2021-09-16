package com.wencoder.security;

import com.wencoder.security.cfg.DefaultSecurityConfig;
import com.wencoder.security.domain.DefaultUserDetails;
import com.wencoder.security.filter.DefaultSecurityFilter;
import com.wencoder.security.handler.DefaultAuthenticationEntryPoint;
import com.wencoder.security.service.DefaultUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
public class SecurityAutoConfig {

    /**
     * 默认用户实现
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultUserDetailsService defaultUserDetailsService() {
        DefaultUserDetails user = new DefaultUserDetails().setId(1).setUsername("test").setPassword("123456")
                .setRoles(Collections.singletonList("Test"));
        log.info("权限认证模块默认账号：{}", user.toString());
        return new DefaultUserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return user;
            }

            @Override
            public UserDetails check(String token) {
                return user;
            }
        };
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
    public DefaultSecurityFilter defaultSecurityFilter() {
        return new DefaultSecurityFilter(defaultUserDetailsService());
    }

    /**
     * 默认权限配置
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultSecurityConfig defaultSecurityConfig() {
        return new DefaultSecurityConfig(authenticationEntryPoint(), defaultSecurityFilter());
    }

}
