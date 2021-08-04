package com.wencoder.tools.exec;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理业务逻辑时，进行抛出的异常。
 *
 * Created by 王林 on 2021-01-29 13:01:31
 */
public class BusinessException extends BaseUncheckedException implements Serializable {

    private static final long serialVersionUID = 5481596588955034193L;

    private BusinessException(Integer code, String message) {
        super(code, message, new HashMap<>());
    }
    private BusinessException(int code, String message, Map<String, Object> extend) {
        super(code, message, extend);
    }

    public static BusinessException busExp(IException ex) {
        return new BusinessException(ex.getCode(), ex.getMessage());
    }
    public static BusinessException busExp(String message) {
        return new BusinessException(ExceptionCode.SYS_BIZ_ERROR.getCode(), message);
    }
    public static BusinessException busExp(int code, String message) {
        return new BusinessException(code, message);
    }
    public static BusinessException busExp(int code, String message, Map<String, Object> extend) {
        return new BusinessException(code, message, extend);
    }
}
