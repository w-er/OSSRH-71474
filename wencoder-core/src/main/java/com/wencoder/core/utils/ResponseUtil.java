package com.wencoder.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wencoder.core.domain.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
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
     * 使用response输出JSON
     *
     * @param response
     * @param result
     */
    public static void print(ServletResponse response, Result<?> result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(result));
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 响应内容
     *
     * @param httpServletResponse
     * @param msg
     * @param status
     */
    public static void print(HttpServletResponse httpServletResponse, Integer status, String msg) {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = httpServletResponse.getWriter()) {
            writer.print(JSONObject.toJSONString(Result.failed(status, msg)));
        } catch (IOException e) {
            log.error("响应报错：{}", e.getMessage());
        }
    }

}
