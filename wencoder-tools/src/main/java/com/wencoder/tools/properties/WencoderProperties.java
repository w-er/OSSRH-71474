package com.wencoder.tools.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置项
 * <p>
 * Created by 王林 on 2021-09-16 19:33:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "wencoder")
public class WencoderProperties {

    /**
     * 文档配置
     */
    private Swagger swagger = new Swagger();

    /**
     * SWAGGER接口文档参数
     */
    @Data
    public static class Swagger {
        /* 基础信息 */
        private Boolean enabled = Boolean.TRUE;
        private String title = "API";
        private String desc = "内容太少了，不好意思看。。。";
        private String basePackage = "com.wencoder.demo.controller";
        /* 联系 */
        private final String name = "王林";
        private final String url = "https://www.wencoder.com";
        private final String email = "encodmail@126.com";
        /* 版本号 */
        private String version = "1.0";
    }
}
