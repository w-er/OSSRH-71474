package com.wencoder.fill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 操作填充的工具类
 * 可能会填充失败，失败会抛出一个 异常，请在使用 fill 填充时进行捕获处理
 */
@Slf4j
public class FillUtil {

    FillSourceContext fillSourceContext;


    /**
     * 创建对象时，必须传入 FillSourceContext ，获取源
     *
     * @param context 源
     */
    public FillUtil(FillSourceContext context) {
        fillSourceContext = context;
    }

    /**
     * 填充
     *
     * @param ret 结果对象
     */
    public void fill(Object ret) throws Exception {
        if (ObjectUtils.isEmpty(ret)) {
            return;
        }
        // 结果转换集合
        Collection<?> col;

        /* 1.处理结果为集合 */
        if (ret instanceof Collection) {
            col = (Collection<?>) ret;
        } else {
            col = Collections.singletonList(ret);
        }
        // 容器,存放解析后的数据
        List<Target> targetList = new ArrayList<>();
        List<Field> fillObjList = new ArrayList<>();
        Map<String, Param> fPMap = new HashMap<>(16);
        Map<String, Result> fRMap = new HashMap<>(16);

        // 返回对象类型，获取父类
        Class<?> objClass = col.iterator().next().getClass();
        if (objClass.isPrimitive()) {
            throwExc("填充类型不能为基础类型");
        }
        Class<?> superClass = objClass.getSuperclass();

        /* 2.获取注解信息 */
        // 获取当前类和父类字段
        Field[] objFields = concat(objClass.getDeclaredFields(), superClass.getDeclaredFields());
        // 获取当前类和父类数据源
        FillSource[] fillSources = concat(concat(objClass.getAnnotation(FillModel.class)), concat(superClass.getAnnotation(FillModel.class)));
        if (fillSources.length == 0) {
            throwExc("没有获取到返回的 POJO， FillModel");
        }
        // 获取字段中的注解信息
        for (Field field : objFields) {
            field.setAccessible(true);
            FillParam fillParam = field.getAnnotation(FillParam.class);
            if (fillParam != null) {
                Param param = new Param(fillParam.value(), field);
                fPMap.put(fillParam.value(), param);
            }
            FillTarget fillTarget = field.getAnnotation(FillTarget.class);
            if (fillTarget != null) {
                Target target = new Target(field, fillTarget.value(), fillTarget.sourceField());
                targetList.add(target);
            }
            // 获取填充对象的字段
            FillObj fillObj = field.getAnnotation(FillObj.class);
            if (fillObj != null) {
                fillObjList.add(field);
            }
        }


        /* 3.处理注解 */
        for (Object obj : col) {
            fPMap.keySet().forEach(key -> {
                Param param = fPMap.get(key);
                try {
                    param.setFieldValue(param.getField().get(obj));
                } catch (IllegalAccessException e) {
                    log.error("填充参数获取异常");
                }
            });
            for (Target target : targetList) {
                target.setFieldValue(fPMap.get(target.getAlias()).getField().get(obj));
            }
        }
        for (FillSource fillSource : fillSources) {
            String name = fillSource.value();
            String[] aliasArr = fillSource.alias();
            FillSourceContext.Invoker invoker = fillSourceContext.getInvoker(name);
            Method method = invoker.getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                continue;
            }
            Object resultList = null;
            Class<?> parameterType = parameterTypes[0];
            if (parameterType.isAssignableFrom(String.class)) {
                List<Object> dataList = new ArrayList<>();
                for (String alias : aliasArr) {
                    Object fieldValue = fPMap.get(alias).getFieldValue();
                    if (ObjectUtils.isNotEmpty(fieldValue)) {
                        dataList.add(fillSourceContext.invoker(name, fieldValue));
                    }
                }
                resultList = dataList;
            } else if (parameterType.isAssignableFrom(List.class)) {
                Class<?> returnType = method.getReturnType();
                if (returnType.isAssignableFrom(List.class) && returnType.isAssignableFrom(Collection.class)) {
                    throwExc("批量方法返回值不是集合");
                }
                List<Object> params = new ArrayList<>();
                for (String alias : fillSource.alias()) {
                    params.add(fPMap.get(alias).getFieldValue());
                }
                resultList = fillSourceContext.invoker(name, params);
            } else {
                throwExc("填充类型不支持");
            }
            // 集合设置
            Collection<?> rCol = (Collection<?>) resultList;
            if (CollectionUtils.isEmpty(rCol)) {
                throwExc("填充数据不存在");
            }
            Map<Object, Object> rMap = new HashMap<>(16);
            for (Object obj : rCol) {
                if (null != obj) {
                    String sourceKey = invoker.getSourceKey();
                    Field key = obj.getClass().getDeclaredField(sourceKey);
                    key.setAccessible(true);
                    Object o = key.get(obj);
                    rMap.put(o, obj);
                }
            }
            Result result = new Result().setAlias(name).setRMap(rMap);
            for (String alias : aliasArr) {
                fRMap.put(alias, result);
            }
        }

        /* 4.结果填充 */
        for (Object obj : col) {
            // 填充目标
            for (Target target : targetList) {
                Field field = target.getField();
                String alias = target.getAlias();
                String sourceField = target.getSourceField();
                // 如果没有设置 sourceField，默认为注解字段
                String fieldName = StringUtils.isNotEmpty(sourceField) ? sourceField : field.getName();
                Object fieldValue = target.getFieldValue();
                Result result = fRMap.get(alias);
                if (result == null || ObjectUtils.isEmpty(fieldValue)) {
                    continue;
                }
                Object r = result.getRMap().get(fieldValue);
                Field targetField = r.getClass().getDeclaredField(fieldName);
                targetField.setAccessible(true);
                Object targetVal = targetField.get(r);
                field.set(obj, targetVal);
            }
            // 填充对象或集合
            for (Field field : fillObjList) {
                field.setAccessible(true);
                try {
                    fill(field.get(obj));
                } catch (Exception e) {
                    log.error("嵌套填充异常：{}", e.getMessage());
                }
            }
        }
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 获取 model 中的数据源
     *
     * @param fillModel 实体
     * @return 源
     */
    private static FillSource[] concat(FillModel fillModel) {
        FillSource[] fillSources;
        if (fillModel == null || fillModel.value() == null || fillModel.value().length == 0) {
            fillSources = new FillSource[]{};
        } else {
            fillSources = fillModel.value();
        }
        return fillSources;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Target {
        Field field;
        String alias;
        String sourceField;
        Object fieldValue;

        public Target(Field field, String alias, String sourceField) {
            this.field = field;
            this.alias = alias;
            this.sourceField = sourceField;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Param {
        Field field;
        String alias;
        Object fieldValue;

        public Param(String alias, Field field) {
            this.alias = alias;
            this.field = field;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    private static class Result {
        String alias;
        Map<Object, Object> rMap;
    }

    /**
     * 统一异常处理
     *
     * @param message 错误信息
     */
    public void throwExc(String message) throws Exception {
        throw new Exception(message);
    }
}
