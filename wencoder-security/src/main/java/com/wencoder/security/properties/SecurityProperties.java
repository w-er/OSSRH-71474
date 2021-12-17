package com.wencoder.security.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 配置项
 * <p>
 * Created by 王林 on 2021-09-16 19:33:40
 */
@ConfigurationProperties(
        prefix = "wencoder.security"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityProperties {

    /**
     * 请求头Token存储key
     */
    public String requestHeaderToken = "Authorization";

    /**
     * 请求头Token 前缀
     */
    public String requestHeaderTokenPrefix = "Bearer ";

    /**
     * 请求头类型
     * application/x-www-form-urlencoded ： form表单格式
     * application/json ： json格式
     */
    public String requestHeaderContentType = "application/json";

    /**
     * URL白名单
     */
    private String[] ignored;

    /**
     * token
     */
    private final Token token = new Token();

    @Data
    public static class Token {
        /**
         * token过期时间（分钟）
         */
        private Integer tokenExpireTime = 60;
        /**
         * 用户选择保存登录状态对应token过期时间（天）
         */
        private Integer saveLoginTime = 7;
        /**
         * 限制用户登陆错误次数（次）
         */
        private Integer loginTimeLimit = 10;
        /**
         * 错误超过次数后多少分钟后才能继续登录（分钟）
         */
        private Integer loginAfterTime = 10;

    }

    public String[] getIgnored() {
        List<String> list = new ArrayList<>();
        if (ignored == null || ignored.length == 0) {
            return ENDPOINTS;
        }
        list.addAll(Arrays.asList(ENDPOINTS));
        list.addAll(Arrays.asList(ignored));
        return list.toArray(new String[0]);
    }

    /**
     * 监控中心和swagger需要访问的url
     */
    private static final String[] ENDPOINTS = {
            "/swagger-resources/**",
            "/swagger-resources/configuration/ui",
            "/swagger-resources",
            "/v2/api-docs",
            "/doc.html",
            "/swagger-resources/configuration/security",
            "/swagger/**",
            "/**/v2/api-docs",
            "/**/*.js",
            "/**/*.css",
            "/**/*.png",
            "/**/*.ico",
            "/webjars/**",
            "/favicon.ico"
    };
}
