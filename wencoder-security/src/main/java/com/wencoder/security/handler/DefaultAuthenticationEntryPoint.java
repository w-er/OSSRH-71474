package com.wencoder.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * <p> 认证权限入口 - 未登录的情况下访问所有接口都会拦截到此 </p>
 * Created by 王林 on 2020-10-24 16:10:03
 */
@Slf4j
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        if (e != null) {
            log.error(e.getMessage());
            print(response, 10401, "未登录，不能访问当前服务！");
        } else {
            print(response, 10402, "无效令牌！");
        }
    }

    /**
     * 错误输出
     *
     * @param response 响应
     * @param code     错误状态码
     * @param msg      错误信息
     */
    public static void print(HttpServletResponse response, Integer code, String msg) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            HashMap<String, Object> result = new HashMap<>();
            result.put("code", code);
            result.put("msg", msg);
            result.put("data", null);
            writer.print(result);
        } catch (IOException e) {
            log.error("响应报错：{}", e.getMessage());
        }
    }
}
