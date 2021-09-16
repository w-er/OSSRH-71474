package com.wencoder.tools.domain;

import com.wencoder.tools.exec.ExceptionCode;
import com.wencoder.tools.exec.IException;
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
@ApiModel(value = "结果集", description = "结果集")
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -8621900780393672869L;

    @ApiModelProperty(value = "状态码：0 - 代表此次请求成功, 其他请参考错误代码信息表")
    private int code = 0;

    @ApiModelProperty(value = "附属信息")
    private String message = "请求成功";

    @ApiModelProperty(position = 2, value = "响应数据")
    private T data;

    private ResultVo() {
    }

    private ResultVo(T data) {
        this.data = data;
    }

    private ResultVo(String message, T data) {
        this.message = message;
        this.data = data;
    }

    private ResultVo(String message) {
        this.message = message;
    }

    private ResultVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResultVo<T> success(T data) {
        return new ResultVo<>(data);
    }

    public static <T> ResultVo<T> success(String message, T data) {
        return new ResultVo<>(message, data);
    }

    public static ResultVo<Object> failed(String message) {
        return new ResultVo<>(ExceptionCode.SYS_BIZ_ERROR.getCode(), message);
    }

    public static ResultVo<Object> failed(Integer code, String message) {
        return new ResultVo<>(code, message);
    }

    public static ResultVo<Object> failed(IException ec) {
        return new ResultVo<>(ec.getCode(), ec.getMessage());
    }
}
