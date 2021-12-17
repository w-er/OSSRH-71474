package com.wencoder.security.cfg;

import com.wencoder.security.filter.DefaultSecurityFilter;
import com.wencoder.security.handler.DefaultAuthenticationEntryPoint;
import com.wencoder.security.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 默认权限配置
 * <p>
 * Created by 王林 on 2021-09-16 16:43:30
 */
@Slf4j
public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {

    protected final DefaultAuthenticationEntryPoint point;
    protected final DefaultSecurityFilter filter;
    protected final SecurityProperties properties;

    public DefaultSecurityConfig(DefaultAuthenticationEntryPoint point,
                                 DefaultSecurityFilter filter,
                                 SecurityProperties properties) {
        this.point = point;
        this.filter = filter;
        this.properties = properties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 1、禁用CSRF 开启跨域
        http.csrf().disable().cors();
        // 2、处理认证失败 和 权限不足 异常情况
        http.exceptionHandling().authenticationEntryPoint(point);
        // 3、不创建会话，通过前端传 token 到后台过滤器中验证是否存在访问权限
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 4、请求权限配置
        http.authorizeRequests()
                // 定义配置类，加载配置类地址
                .antMatchers(properties.getIgnored()).permitAll()
                // 剩下的所有请求都需要认证才能访问
                .anyRequest().authenticated();
        // 5、配置资源权限
//        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//            @Override
//            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
//                o.setAccessDecisionManager(urlAccessDecisionManager);
//                return o;
//            }
//        });
        // 6、自定义过滤器在登录时认证用户名、密码
        http.addFilterBefore(filter, BasicAuthenticationFilter.class);
    }

}
