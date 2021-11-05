package com.wencoder.tools.exec.conf;

import com.wencoder.tools.domain.ResultVo;
import com.wencoder.tools.exec.BaseUncheckedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static com.wencoder.tools.domain.ResultVo.failed;


/**
 * 异常处理器
 * <p>
 * Created by 王林 on 2021-01-29 13:01:33
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * 系统异常处理
     *
     * @param ex 全局异常
     * @return 错误信息
     */
    @ExceptionHandler(value = Exception.class)
    public ResultVo<Object> handle(Exception ex) {
        ex.printStackTrace();
        log.error("全局异常：{}", ex.getMessage());
        return failed("系统错误");
    }

    /**
     * 运行时异常捕获
     *
     * @param ex 业务异常
     * @return 错误信息
     */
    @ExceptionHandler(BaseUncheckedException.class)
    public ResultVo<Object> handle(BaseUncheckedException ex) {
        log.error("业务异常：{}", ex.getMessage());
        return failed(ex);
    }

    /**
     * 文件上传不符合要求
     *
     * @param ex 文件异常
     * @return 错误信息
     */
    @ExceptionHandler(value = {MultipartException.class})
    public ResultVo<Object> handle(MultipartException ex) {
        log.error("文件异常：{}", ex.getMessage());
        return failed("文件不符合要求");
    }

    /**
     * 访问路径被篡改
     *
     * @param ex 参数异常
     * @return 错误信息
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResultVo<Object> handle(MethodArgumentTypeMismatchException ex) {
        log.error("文件异常：{}", ex.getMessage());
        return failed("参数不匹配,请填写正确路径");
    }

    /**
     * 数据绑定异常
     *
     * @param ex 参数异常
     * @return 错误信息
     */
    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResultVo<Object> handle(ServletRequestBindingException ex) {
        log.error("参数异常：{}", ex.getMessage());
        return failed("请求参数错误");
    }

    /**
     * 请求异常
     *
     * @param ex1 请求的方法不存在
     * @param ex2 不支持的媒体类型
     * @param ex3 数字格式异常
     * @return 错误信息
     */
    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            NumberFormatException.class,
    })
    public ResultVo<Object> handle(
            HttpRequestMethodNotSupportedException ex1,
            HttpMediaTypeNotSupportedException ex2,
            NumberFormatException ex3
    ) {
        log.error("请求异常：{}", ex1.getMessage());
        log.error("请求异常：{}", ex2.getMessage());
        log.error("请求异常：{}", ex3.getMessage());
        return failed("请求有误");
    }

    /**
     * 请求数据体为空
     *
     * @param ex 请求异常
     * @return 错误信息
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultVo<Object> handle(HttpMessageNotReadableException ex) {
        log.error("请求异常：{}", ex.getMessage());
        return failed("数据体不能为空");
    }

    /**
     * 校验异常
     * 处理 @PathVariable和@RequestParam  验证不通过抛出的异常
     *
     * @param ex 参数异常
     * @return 错误信息
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVo<Object> handle(ConstraintViolationException ex) {
        List<String> errorInformation = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        String error = errorInformation.get(0);
        log.error("参数校验异常：{}", error);
        return failed(error);
    }

    /**
     * 校验异常
     *
     * @param ex 参数绑定异常
     * @return 错误信息
     */
    @ExceptionHandler(BindException.class)
    public ResultVo<Object> handle(BindException ex) {
        String error = ex.getAllErrors().get(0).getDefaultMessage();
        log.error("参数绑定异常：{}", error);
        return failed(error);
    }

    /**
     * 校验异常
     * 处理 @RequestBody ，验证不通过抛出的异常
     *
     * @param ex 参数校验异常
     * @return 错误结果集
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo<Object> handle(MethodArgumentNotValidException ex) {
        List<String> collect = ex.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
        String error = collect.get(0);
        log.error("参数校验异常：{}", error);
        return failed(error);
    }
}

