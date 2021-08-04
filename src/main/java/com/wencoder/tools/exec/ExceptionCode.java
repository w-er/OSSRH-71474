package com.wencoder.tools.exec;

/**
 * 常用异常码
 * <p>
 * Created by 王林 on 2021-01-29 13:01:54
 */
public enum ExceptionCode implements IException {

    SUCCESSFUL(0, "请求成功"),
    SYS_BIZ_ERROR(-1, "系统错误"),
    SYS_ERROR(100500, "系统错误"),
    ;

    private final int code;

    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 通过code返回枚举
     */
    public static ExceptionCode value(int code) {
        ExceptionCode[] values = values();
        for (ExceptionCode error : values) {
            if (error.getCode() == code) {
                return error;
            }
        }
        // 如果没有这个返回结果那么我将默认返回 500 错误
        return SYS_ERROR;
    }
}
