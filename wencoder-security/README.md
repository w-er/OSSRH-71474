### 功能说明

- 提供默认安全前后分类权限认证配置

### 使用

在启动类配置注解即可

```java

@EnableSecurityCfg //此处
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```