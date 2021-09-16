package com.wencoder.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 默认权限用户持久接口
 * 业务必须实现这个
 * <p>
 * Created by 王林 on 2021-09-16 16:44:44
 */
public interface DefaultUserDetailsService extends UserDetailsService {

    /**
     * 检查是否token是否有效
     *
     * @param token 令牌
     * @return 用户信息
     */
    UserDetails check(String token);

}
