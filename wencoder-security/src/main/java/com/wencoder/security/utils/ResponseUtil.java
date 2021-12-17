package com.wencoder.security.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p> 使用response输出JSON </p>
 * <p>
 * Created by 王林 on 2020-10-24 17:10:17
 */
@Slf4j
public class ResponseUtil {

    /**
     * 响应内容
     *
     * @param response
     * @param msg
     * @param status
     */
    public static void print(HttpServletResponse response, Integer status, String msg) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(status);
        try {
            writer = response.getWriter();
            writer.print(msg);
        } catch (IOException e) {
            log.error("响应报错：{}", e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

}
