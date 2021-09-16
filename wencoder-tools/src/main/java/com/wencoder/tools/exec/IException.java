package com.wencoder.tools.exec;

/**
 * 异常规范
 * <p>
 * Created by 王林 on 2021-01-14 11:01:39
 */
public interface IException {
    /**
     * @return 异常编码
     */
    int getCode();

    /**
     * @return 异常消息
     */
    String getMessage();
}
