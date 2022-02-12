package com.wencoder.core.exec;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 运行时异常，基类
 * <p>
 * Created by 王林 on 2021-01-29 13:01:14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseUncheckedException extends RuntimeException implements IError {

    private int code;

    private String msg;

    private Map<String, Object> extend;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BaseUncheckedException(int code, String msg, Map<String, Object> extend) {
        this.code = code;
        this.msg = msg;
        this.extend = extend;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
