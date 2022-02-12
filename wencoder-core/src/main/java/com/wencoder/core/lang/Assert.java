package com.wencoder.core.lang;

import com.wencoder.core.domain.Result;
import com.wencoder.core.exec.BusinessException;
import com.wencoder.core.exec.IError;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 断言<br>
 * 断言某些对象或值是否符合规定，否则抛出异常。经常用于做变量检查
 *
 * @author 王林
 */
public class Assert {

    // -----------------------
    // 大于
    // -----------------------

    /**
     * 大于O
     */
    public static void gtz(Integer num, IError error) {
        if (num == null || num > 0) {
            fail(error);
        }
    }

    /**
     * 大于等于O
     */
    public static void gez(Integer num, IError error) {
        if (num == null || num >= 0) {
            fail(error);
        }
    }

    /**
     * num1大于num2
     */
    public static void gt(Integer num1, Integer num2, IError error) {
        if (num1 > num2) {
            fail(error);
        }
    }

    /**
     * num1大于等于num2
     */
    public static void ge(Integer num1, Integer num2, IError error) {
        if (num1 >= num2) {
            fail(error);
        }
    }

    // -----------------------
    // 小于
    // -----------------------

    /**
     * 小于O
     */
    public static void ltz(Integer num, IError error) {
        if (num == null || num < 0) {
            fail(error);
        }
    }

    /**
     * 小于等于O
     */
    public static void lez(Integer num, IError error) {
        if (num == null || num <= 0) {
            fail(error);
        }
    }

    /**
     * num1小于num2
     */
    public static void lt(Integer num1, Integer num2, IError error) {
        if (num1 < num2) {
            fail(error);
        }
    }

    /**
     * num1小于等于num2
     */
    public static void le(Integer num1, Integer num2, IError error) {
        if (num1 <= num2) {
            fail(error);
        }
    }

    // -----------------------
    // 逻辑判断
    // -----------------------

    /**
     * 判断 expression 是否为 True,如果为真则抛出错误 IError
     * @param expression 判断的条件
     * @param error 要抛出的错误
     */
    public static void isTrue(boolean expression, IError error) {
        if (expression) {
            fail(error);
        }
    }

    /**
     * 判断 expression 是否为 False,如果为假则抛出错误 False
     * @param expression 判断的条件
     * @param error 要抛出的错误
     */
    public static void isFalse(boolean expression, IError error) {
        if (!expression) {
            fail(error);
        }
    }

    // -----------------------
    // 对象判断
    // -----------------------

    /**
     * 判断目标对象是空的，则抛出 IError
     * @param obj 目标对象
     * @param error 要抛出的错误
     * @param <T> 返回的数据类型
     * @return T
     */
    public static <T> T isEmpty(T obj, IError error) {
        isTrue(ObjectUtils.isEmpty(obj), error);
        return obj;
    }

    /**
     * 判断目标对象不为空是，则抛出IError
     * @param obj 目标对象
     * @param error 要抛出的错误
     */
    public static void notEmpty(Object obj, IError error) {
        isTrue(ObjectUtils.isNotEmpty(obj), error);
    }

    /**
     * 判断两个字符相同，相同则抛出IError
     * @param cs1 字符1
     * @param cs2 字符2
     * @param error 要抛出的错误
     */
    public static void equals(final CharSequence cs1, final CharSequence cs2, IError error) {
        isTrue(StringUtils.equals(cs1, cs2), error);
    }

    /**
     * 判断两个字符是不相同，相同则抛出IError
     * @param cs1 字符1
     * @param cs2 字符2
     * @param error 要抛出的错误
     */
    public static void notEquals(final CharSequence cs1, final CharSequence cs2, IError error) {
        isFalse(StringUtils.equals(cs1, cs2), error);
    }

    /**
     * 判断结果是否错误，错误抛出结果中的异常信息
     *
     * @param result 判断的结果信息
     */
    public static void isError(Result<?> result) {
        if (result.isError()) {
            throw BusinessException.busExp(result);
        }
    }


    /**
     * 失败结果
     *
     * @param error 异常信息
     */
    public static void fail(IError error) {
        throw BusinessException.busExp(error.getCode(), error.getMsg());
    }
}
