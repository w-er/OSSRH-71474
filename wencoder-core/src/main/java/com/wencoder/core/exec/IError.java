package com.wencoder.core.exec;

/**
 * 错误规范
 * <p>
 * Created by 王林 on 2021-01-14 11:01:39
 */
public interface IError {

    /**
     * @return 异常编码
     */
    int getCode();

    /**
     * @return 异常消息
     */
    String getMsg();

}
