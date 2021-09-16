package com.wencoder.tools;

import com.wencoder.tools.exec.conf.ExceptionHandlerAdvice;
import com.wencoder.tools.utils.CopyUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 核心组件的入口配置类
 * <p>
 * 说明：
 * ConditionalOnBean         //	当给定的在bean存在时,则实例化当前Bean
 * ConditionalOnMissingBean  //	当给定的在bean不存在时,则实例化当前Bean
 * ConditionalOnClass        //	当给定的类名在类路径上存在，则实例化当前Bean
 * ConditionalOnMissingClass //	当给定的类名在类路径上不存在，则c例化当前Bean
 */
@Import({

})
@Configuration
// 导入我们自定义的配置类,供当前类使用
@EnableConfigurationProperties()
// 当存在某个类时，此自动配置类才会生效,这里可以使用外部的String类名
//@ConditionalOnMissingBean(SpringUtil.class)
// 只有web应用程序时此自动配置类才会生效
@ConditionalOnWebApplication
public class ToolsConfig {

    @Bean
    @ConditionalOnMissingBean
    public CopyUtil springUtil() {
        return new CopyUtil();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionHandlerAdvice exceptionHandlerAdvice() {
        return new ExceptionHandlerAdvice();
    }

    /**
     * 跨域
     */
    @Bean
    @ConditionalOnMissingBean
    public CorsFilter cors() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        /* 允许cookies跨域*/
        config.setAllowCredentials(true);
        /* 允许向该服务器提交请求的URI，*表示全部允许*/
        // 携带cookie不能写*，可以写0
        config.addAllowedOrigin("*");
        /* 允许访问的头信息,*表示全部*/
        config.addAllowedHeader("*");
        /* 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了*/
        config.setMaxAge(18000L);
        /* 允许提交请求的方法，*表示全部允许*/
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
