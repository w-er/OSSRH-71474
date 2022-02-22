package com.wencoder.core;

import com.wencoder.core.exec.conf.ExceptionHandlerAdvice;
import com.wencoder.core.utils.CopyUtil;
import com.wencoder.core.utils.SpringUtil;
import io.swagger.models.Swagger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

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
@Order(-1)
@Configuration
@EnableSwagger2
// 导入我们自定义的配置类,供当前类使用
@EnableConfigurationProperties({ToolsProperties.class})
// 当存在某个类时，此自动配置类才会生效,这里可以使用外部的String类名
//@ConditionalOnMissingBean(SpringUtil.class)
// 只有web应用程序时此自动配置类才会生效
@ConditionalOnWebApplication
public class ToolsConfig {

    @Bean
    @ConditionalOnMissingBean
    public CopyUtil copyUtil() {
        return new CopyUtil();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExceptionHandlerAdvice exceptionHandlerAdvice() {
        return new ExceptionHandlerAdvice();
    }

    /**
     * Spring boot 2.5.4 跨域默认处理
     */
    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //1,允许任何来源
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        //2,允许任何请求头
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
        //3,允许任何方法
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
        //4,允许凭证
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Bean
    @ConditionalOnMissingBean
    public Docket docket(ToolsProperties properties) {
        ToolsProperties.Swagger swagger = properties.getSwagger();
        ApiInfo api = new ApiInfoBuilder()
                .title(swagger.getTitle())
                .description(swagger.getDesc())
                .contact(new Contact(swagger.getName(), swagger.getUrl(), swagger.getEmail()))
                .version(swagger.getVersion())
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(api)
                .enable(swagger.getEnabled()).select()
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    @Primary
    @ConditionalOnClass(value = {Swagger.class})
    public SwaggerResourcesProvider swaggerResourcesProvider(ToolsProperties properties) {
        return () -> {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName("API");
            swaggerResource.setLocation("/v2/api-docs");
            swaggerResource.setSwaggerVersion(properties.getSwagger().getVersion());
            return Collections.singletonList(swaggerResource);
        };
    }

    /**
     * swagger 重复方法日志隐藏
     * <p>
     * Created by 王林 on 2021-11-25 14:38:18
     */
    @Bean
    @ConditionalOnMissingBean
    public SwaggerChangeOperationNameGenerator swaggerChangeOperationNameGenerator() {
        return new SwaggerChangeOperationNameGenerator();
    }
}
