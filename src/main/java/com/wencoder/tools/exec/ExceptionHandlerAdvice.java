package com.wencoder.tools.exec;

import com.wencoder.tools.domain.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
import static com.wencoder.tools.exec.ExceptionCode.*;


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
     * @return 错误结果集
     */
    @ExceptionHandler(value = Exception.class)
    public ResultVo<Object> handleException(Exception ex) {
        return failed(ex.getMessage());
    }

    /**
     * 运行时异常捕获
     *
     * @param ex 业务异常
     * @return 错误结果集
     */
    @ExceptionHandler(BaseUncheckedException.class)
    public ResultVo<Object> handleBaseException(BaseUncheckedException ex) {
        return failed(ex);
    }


//    /**
//     * JSON failure
//     *
//     * @param ex      JSON异常
//     * @param request 请求
//     * @return 错误结果集
//     */
//    @ExceptionHandler(value = JSONException.class)
//    public ResultVo<Object> handleJSONException(JSONException ex, HttpServletRequest request) {
//        return failed(SYS_PARSING_FAILS);
//    }



    /**
     * 文件上传不符合要求
     *
     * @param ex 文件异常
     * @return 错误结果集
     */
    @ExceptionHandler(value = {MultipartException.class})
    public ResultVo<Object> handleMultipartException(MultipartException ex) {
        return failed(SYS_DOCUMENT_DOES_NOT_MEET_THE_REQUIREMENTS);
    }

    /**
     * 访问路径被篡改
     *
     * @param ex 参数异常
     * @return 错误结果集
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResultVo<Object> handleNumberFormatException(MethodArgumentTypeMismatchException ex) {
        return failed(SYS_PARAMETERS_DO_NOT_MATCH_PLEASE_FILL_IN_THE_CORRECT_PATH);
    }

    /**
     * 数据绑定异常
     *
     * @param ex 参数异常
     * @return 错误结果集
     */
    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResultVo<Object> handleServletRequestBindingException(ServletRequestBindingException ex) {
        return failed(SYS_REQUEST_PARAMETER_IS_INCORRECT);
    }

    /**
     * 请求的方法不存在
     *
     * @return 错误结果集
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultVo<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return failed(SYS_REQUEST_IS_WRONG);
    }

    /**
     * 不支持的媒体类型
     *
     * @return 错误结果集
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResultVo<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return failed(SYS_REQUEST_IS_WRONG);
    }

    /**
     * 错误的数字格式
     *
     * @param ex 数字格式异常
     * @return 错误结果集
     */
    @ExceptionHandler(NumberFormatException.class)
    public ResultVo<Object> handleNumberFormatException(NumberFormatException ex) {
        return failed(SYS_REQUEST_IS_WRONG);
    }

    /**
     * 请求数据体为空
     *
     * @param ex 请求异常
     * @return 错误结果集
     */
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResultVo<Object> handleAccessDeniedException(HttpMessageNotReadableException ex) {
        return failed(SYS_DATA_BODY_CANNOT_BE_EMPTY);
    }

    /**
     * 校验异常
     * 处理 @PathVariable和@RequestParam  验证不通过抛出的异常
     *
     * @param ex 参数异常
     * @return 错误结果集
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVo<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorInformation = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
        return failed(ExceptionCode.SYS_MEDIA_TYPE_EX.getCode(), errorInformation.get(0));
    }

    /**
     * 校验异常
     *
     * @param ex 参数绑定异常
     * @return 错误结果集
     */
    @ExceptionHandler(BindException.class)
    public ResultVo<Object> handleBindException(BindException ex) {
        return failed(ex.getAllErrors().get(0).getDefaultMessage());
    }


}

