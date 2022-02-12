package com.wencoder.security.filter;


import cn.hutool.jwt.JWTException;
import com.wencoder.security.handler.DefaultAuthenticationEntryPoint;
import com.wencoder.security.properties.SecurityProperties;
import com.wencoder.security.service.DefaultUserDetailsService;
import com.wencoder.security.token.DefaultWCToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 访问鉴权 - 每次访问接口都会经过此 </p>
 * Created by 王林 on 2020-10-24 16:10:14
 */
@Slf4j
public class DefaultSecurityFilter extends OncePerRequestFilter {

    protected final DefaultUserDetailsService service;
    protected final DefaultAuthenticationEntryPoint entryPoint;
    protected final SecurityProperties properties;

    public DefaultSecurityFilter(
            DefaultUserDetailsService service, DefaultAuthenticationEntryPoint entryPoint, SecurityProperties properties) {
        this.service = service;
        this.entryPoint = entryPoint;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        setResponseHeader(request, response);
        // Vue 会对接口进行校验
        if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
            try {
                String bearerToken = request.getHeader(properties.getRequestHeaderToken());
                if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(properties.getRequestHeaderTokenPrefix())) {
                    String token = bearerToken.substring(properties.getRequestHeaderTokenPrefix().length());
                    UserDetails detail = service.check(token, request);
                    DefaultWCToken authentication = new DefaultWCToken(detail, detail.getAuthorities());
                    // 全局注入角色权限信息和登录用户基本信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                filterChain.doFilter(request, response);
            } catch (JWTException e) {
                SecurityContextHolder.clearContext();
                this.entryPoint.commence(request, response, null);
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext();
                this.entryPoint.commence(request, response, e);
            }
        }
    }

    /**
     * 设置响应数据头
     */
    private void setResponseHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type, x-requested-with, X-Custom-Header, Request-Ajax, " + properties.getRequestHeaderToken());
    }
}
