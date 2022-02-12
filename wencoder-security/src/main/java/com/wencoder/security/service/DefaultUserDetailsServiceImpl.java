package com.wencoder.security.service;

import com.wencoder.security.domain.DefaultUserDetails;
import com.wencoder.security.domain.LoginUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * 用户默认信息实现
 * <p>
 * Created by 王林 on 2021-09-17 10:39:20
 */
@Slf4j
@AllArgsConstructor
public class DefaultUserDetailsServiceImpl implements DefaultUserDetailsService {

    /**
     * 默认登录用户信息
     */
    private DefaultUserDetails<LoginUser, GrantedAuthority> defaultUser;

    public DefaultUserDetailsServiceImpl() {
        LoginUser loginUser = new LoginUser().setUsername("test").setPassword("123456");
        GrantedAuthority authority = new SimpleGrantedAuthority("Test");
        defaultUser = new DefaultUserDetails<>()
                .setLoginUser(loginUser)
                .setRoles(Collections.singletonList(authority));
        log.info("权限认证模块默认账号：{}", defaultUser.toString());
    }

    @Override
    public DefaultUserDetails<LoginUser, GrantedAuthority> loadUserByUsername(String username) throws UsernameNotFoundException {
        return defaultUser;
    }

    @Override
    public DefaultUserDetails<LoginUser, GrantedAuthority> check(String token, HttpServletRequest request) {
        return defaultUser;
    }
}
