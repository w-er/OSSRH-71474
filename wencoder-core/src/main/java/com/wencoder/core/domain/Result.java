package com.wencoder.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wencoder.core.exec.ErrorCode;
import com.wencoder.core.exec.IError;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 规定成功响应此结果
 * <p>
 * Created by 王林 on 2021-01-14 11:01:35
 */
@Data
@ApiModel(value = "结果", description = "结果")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -8621900780393672869L;

    @ApiModelProperty(value = "状态码：0 - 代表此次请求成功, 其他请参考错误代码信息表")
    private int code = 0;

    @ApiModelProperty(value = "信息")
    private String msg = "成功";

    @ApiModelProperty(position = 2, value = "数据")
    private T data;

    private Result() {
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }

    private Result(String msg) {
        this.msg = msg;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(msg, data);
    }

    public static Result<Object> failed(String msg) {
        return new Result<>(ErrorCode.SYS_BIZ_ERROR.getCode(), msg);
    }

    public static Result<Object> failed(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public static Result<Object> failed(IError ec) {
        return new Result<>(ec.getCode(), ec.getMsg());
    }

    @JsonIgnore
    public boolean isSuccessful() {
        return 0 == this.code;
    }

    @JsonIgnore
    public boolean isError() {
        return !this.isSuccessful();
    }
}
