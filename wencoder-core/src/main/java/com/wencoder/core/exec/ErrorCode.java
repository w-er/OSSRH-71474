package com.wencoder.core.exec;

/**
 * 常用异常码
 * <p>
 * Created by 王林 on 2021-01-29 13:01:54
 */
public enum ErrorCode implements IError {

    SUCCESSFUL(0, "请求成功"),
    SYS_BIZ_ERROR(-1, "业务错误"),
    SYS_ERROR(1005, "系统发生未知错误！"),
    ;

    /**
     * 错误代码
     */
    private final int code;

    /**
     * 错误提示
     */
    private final String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    /**
     * 通过code返回枚举
     */
    public static ErrorCode value(int code) {
        ErrorCode[] values = values();
        for (ErrorCode error : values) {
            if (error.getCode() == code) {
                return error;
            }
        }
        // 如果没有这个返回结果那么我将默认返回 500 错误
        return SYS_ERROR;
    }
}
