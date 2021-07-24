package com.wencoder.tools.exec;

/**
 * 常用异常码
 *
 * Created by 王林 on 2021-01-29 13:01:54
 */
public enum ExceptionCode implements IException {

    SYS_MEDIA_TYPE_EX(100401, "请求参数类型异常"),
    SYS_SQL_EX(100402, "运行SQL出现异常"),
    SYS_NULL_POINT_EX(100003, "空指针异常"),
    SYS_SYNTAX_ERROR(100404,"请求参数不匹配"),
    SYS_UNAUTHENTICATED(100405,"未经授权"),
    SYS_NOT_EXIST400(100406,"请求参数类型匹配错误"),
    SYS_ACCESS_DENIED(100407,"服务器拒绝访问"),
    SYS_NOT_EXIST(100408,"没有找到资源"),
    SYS_NOT_EXIST405(100409,"不支持当前请求类型"),
    SYS_REQUEST_MODE_INCORRECT(100410,"请求方式不正确"),
    SYS_FEIGN_ERROR(100411,"服务降级通知"),
    SYS_OPERATION_FAIL(100412,"操作失败"),
    SYS_NO_DEL(100413,"该数据不能删除"),
    SYS_NO_DATA(100414,"数据不存在"),
    SYS_REQUEST_TIMEOUT_OR_BUSINESS_CIRCUIT_BREAKER_OCCURS(100415, "请求超时或发生业务熔断"),
    SYS_REQUEST_TIMEOUT(100416, "请求超时"),
    SYS_PARSING_FAILS(100418, "JSON解析失败"),
    SYS_DOCUMENT_DOES_NOT_MEET_THE_REQUIREMENTS(100419, "文件不符合要求"),
    SYS_PARAMETERS_DO_NOT_MATCH_PLEASE_FILL_IN_THE_CORRECT_PATH(100420, "参数不匹配,请填写正确路径"),
    SYS_REQUEST_PARAMETER_IS_INCORRECT(100421, "请求参数不正确"),
    SYS_REQUEST_IS_WRONG (100422, "请求有误"),
    SYS_DATA_BODY_CANNOT_BE_EMPTY (100423, "数据体不能为空"),
    SYS_SERVER_OVERLOAD_OR_MAINTENANCE(100424,"服务器超负载或停机维护"),
    SYS_ACCESS_DENIED_EXCEPTION(100425, "不允许访问"),
    SYS_SERVICE_BUSY(400500, "服务繁忙"),
    SYS_EXECUTING_REQUEST_ERROR(100500, "服务无响应"),
    SYS_INTERNAL_SERVER_ERROR(100500, "sorry, unknown error has occurred!"),
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
    public static ExceptionCode parse(int code){
        ExceptionCode[] values = values();
        for (ExceptionCode error : values) {
            if(error.getCode() == code){
                return error;
            }
        }
        // 如果没有这个返回结果那么我将默认返回 500 错误
        return SYS_INTERNAL_SERVER_ERROR;
    }
}
