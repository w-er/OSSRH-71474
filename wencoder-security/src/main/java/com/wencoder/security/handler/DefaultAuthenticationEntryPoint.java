package com.wencoder.security.handler;

import com.wencoder.tools.domain.ResultVo;
import com.wencoder.tools.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> 认证权限入口 - 未登录的情况下访问所有接口都会拦截到此 </p>
 * Created by 王林 on 2020-10-24 16:10:03
 */
@Slf4j
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        // 未登录 或 token过未登录期
        if (e != null) {
            ResponseUtil.out(response, ResultVo.failed(401, "未登录情况不能访问当前服务！"));
//            ResponseUtil.out(response, ResultVo.failed(401, e.getMessage()));
        } else {
            ResponseUtil.out(response, ResultVo.failed(403, "Token过期!"));
        }
    }

}
