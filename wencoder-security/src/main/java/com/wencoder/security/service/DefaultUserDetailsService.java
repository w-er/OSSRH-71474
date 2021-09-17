package com.wencoder.security.service;

import com.wencoder.security.domain.DefaultUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;

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
     * @param request 请求
     * @return 用户信息
     */
    DefaultUserDetails check(String token, HttpServletRequest request);

}
