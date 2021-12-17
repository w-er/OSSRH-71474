package com.wencoder.security.service;

import com.wencoder.security.domain.DefaultUserDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户默认信息实现
 * <p>
 * Created by 王林 on 2021-09-17 10:39:20
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class DefaultUserDetailsServiceImpl implements DefaultUserDetailsService {

    // 默认用户
    private DefaultUserDetails defaultUser;

    @Override
    public DefaultUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return defaultUser;
    }

    @Override
    public DefaultUserDetails check(String token, HttpServletRequest request) {
        return defaultUser;
    }
}
