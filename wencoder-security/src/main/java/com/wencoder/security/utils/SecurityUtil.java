package com.wencoder.security.utils;

import com.wencoder.security.domain.DefaultUserDetails;
import com.wencoder.security.exce.DefaultAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取当前登录用户存入 的信息
 * <p>
 * Created by 王林 on 2020-10-24 17:10:17
 */
public class SecurityUtil {

    public static DefaultUserDetails principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultUserDetails) {
            return (DefaultUserDetails) principal;
        }
        throw DefaultAuthenticationException.exception("请先登录！");
    }

    public static Integer getAuthUserId() {
        return principal().getId();
    }

}
