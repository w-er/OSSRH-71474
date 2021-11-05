package com.wencoder.tools.pool;

/**
 * 常用字符串常量定义
 * <p>
 * Created by 王林 on 2021-09-16 10:49:19
 */
public interface StrPool {

    /* 扩展字符常量 */
    /**
     * 获取项目根目录
     */
    String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir");

    /**
     * 请求头Token存储key
     */
    String REQUEST_AUTHORIZATION = "Authorization";

    /**
     * 请求头Token 前缀
     */
    String REQUEST_TOKEN_PREFIX = "Bearer ";

    /**
     * 请求头类型
     * application/x-www-form-urlencoded ： form表单格式
     * application/json ： json格式
     */
    String REQUEST_HEADERS_CONTENT_TYPE = "application/json";
}
