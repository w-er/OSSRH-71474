package com.wencoder.core.exec;

import com.wencoder.core.domain.Result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理业务逻辑时，进行抛出的异常。
 * <p>
 * Created by 王林 on 2021-01-29 13:01:31
 */
public class BusinessException extends BaseUncheckedException implements Serializable {

    private static final long serialVersionUID = 5481596588955034193L;

    private BusinessException(Integer code, String msg) {
        super(code, msg, new HashMap<>());
    }

    private BusinessException(int code, String msg, Map<String, Object> extend) {
        super(code, msg, extend);
    }

    public static BusinessException busExp(IError ex) {
        return new BusinessException(ex.getCode(), ex.getMsg());
    }

    public static BusinessException busExp(Result<?> result) {
        return new BusinessException(result.getCode(), result.getMsg());
    }

    public static BusinessException busExp(String msg) {
        return new BusinessException(ErrorCode.SYS_BIZ_ERROR.getCode(), msg);
    }

    public static BusinessException busExp(int code, String msg) {
        return new BusinessException(code, msg);
    }

    public static BusinessException busExp(int code, String msg, Map<String, Object> extend) {
        return new BusinessException(code, msg, extend);
    }
}
