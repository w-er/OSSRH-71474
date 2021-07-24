package com.wencoder.tools;

import com.wencoder.tools.utils.CopyUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 核心组件的入口配置类
 * <p>
 * 说明：
 * ConditionalOnBean         //	当给定的在bean存在时,则实例化当前Bean
 * ConditionalOnMissingBean  //	当给定的在bean不存在时,则实例化当前Bean
 * ConditionalOnClass        //	当给定的类名在类路径上存在，则实例化当前Bean
 * ConditionalOnMissingClass //	当给定的类名在类路径上不存在，则实例化当前Bean
 */
@Import({

})
@Configuration
// 导入我们自定义的配置类,供当前类使用
@EnableConfigurationProperties({

})
// 当存在某个类时，此自动配置类才会生效,这里可以使用外部的String类名
//@ConditionalOnMissingBean(SpringUtil.class)
// 只有web应用程序时此自动配置类才会生效
@ConditionalOnWebApplication
public class ToolsConfig {

    @Bean
    @ConditionalOnMissingBean
    public CopyUtil springUtil() {
        return new CopyUtil();
    }

}
