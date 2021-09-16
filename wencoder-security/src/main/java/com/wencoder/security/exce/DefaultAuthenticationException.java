package com.wencoder.security.exce;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;

/**
 * 权限异常
 * <p>
 * Created by 王林 on 2021-09-16 16:43:59
 */
@Slf4j
public class DefaultAuthenticationException extends AuthenticationException {
    private static final long serialVersionUID = 5022575393500654458L;

    @Getter
    @Setter
    private String data;

    public DefaultAuthenticationException(String message) {
        super(message);
    }

    public DefaultAuthenticationException(String message, String data) {
        super(message);
        this.data = data;
    }

    public static DefaultAuthenticationException exception(String message) {
        return new DefaultAuthenticationException(message);
    }

    public static DefaultAuthenticationException exception(String message, String data) {
        return new DefaultAuthenticationException(message, data);
    }

}
