package com.wencoder.security.filter;


import com.wencoder.security.service.DefaultUserDetailsService;
import com.wencoder.security.token.DefaultWencoderToken;
import com.wencoder.tools.constant.StrWencoderPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * <p> 访问鉴权 - 每次访问接口都会经过此 </p>
 * Created by 王林 on 2020-10-24 16:10:14
 */
@Slf4j
public class DefaultSecurityFilter extends OncePerRequestFilter {

    private final DefaultUserDetailsService service;

    public DefaultSecurityFilter(DefaultUserDetailsService service) {
        this.service = service;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        if ((request.getContentType() == null
                && request.getContentLength() > 0)
                || (request.getContentType() != null
                && !request.getContentType().contains(StrWencoderPool.REQUEST_HEADERS_CONTENT_TYPE))) {
            filterChain.doFilter(request, response);
        }
        // 获取请求头信息 Authorization 信息
        String bearerToken = request.getHeader(StrWencoderPool.REQUEST_AUTHORIZATION);
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(StrWencoderPool.REQUEST_TOKEN_PREFIX)) {
            String token = bearerToken.substring(7);
            UserDetails detail = service.check(token);
            DefaultWencoderToken authentication = new DefaultWencoderToken(detail, detail.getAuthorities());
            // 全局注入角色权限信息和登录用户基本信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
