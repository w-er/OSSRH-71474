package com.wencoder.tools.utils;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import ma.glasnost.orika.metadata.Type;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 映射工具类
 *
 * Created by 王林 on 2021-01-29 13:01:14
 */
@Slf4j
public class CopyUtil {

    /**
     * 字段实例
     */
    public static MapperFacade mapperFacade;

    /**
     * 字段实例集合
     */
    private static final Map<String, MapperFacade> CACHE_MAPPER_FACADE_MAP = new ConcurrentHashMap<>();

    /**
     * 解决 orika 映射 LocalDateTime 报错
     */
    @PostConstruct
    public void init() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.getConverterFactory().registerConverter(new LocalDateTimeConverter());
        mapperFactory.getConverterFactory().registerConverter(new LocalDateConverter());
        mapperFactory.getConverterFactory().registerConverter(new LocalTimeConverter());
        mapperFacade = mapperFactory.getMapperFacade();
    }

    private static class LocalDateTimeConverter extends BidirectionalConverter<LocalDateTime, LocalDateTime> {
        @Override
        public LocalDateTime convertTo(LocalDateTime source, Type<LocalDateTime> destinationType) {
            return LocalDateTime.from(source);
        }

        @Override
        public LocalDateTime convertFrom(LocalDateTime source, Type<LocalDateTime> destinationType) {
            return LocalDateTime.from(source);
        }
    }

    private static class LocalDateConverter extends BidirectionalConverter<LocalDate, LocalDate> {
        @Override
        public LocalDate convertTo(LocalDate source, Type<LocalDate> destinationType) {
            return LocalDate.from(source);
        }

        @Override
        public LocalDate convertFrom(LocalDate source, Type<LocalDate> destinationType) {
            return LocalDate.from(source);
        }
    }

    private static class LocalTimeConverter extends BidirectionalConverter<LocalTime, LocalTime> {
        @Override
        public LocalTime convertTo(LocalTime source, Type<LocalTime> destinationType) {
            return LocalTime.from(source);
        }

        @Override
        public LocalTime convertFrom(LocalTime source, Type<LocalTime> destinationType) {
            return LocalTime.from(source);
        }
    }

    /**
     * 映射实体（默认字段）
     *
     * @param toClass 映射类对象
     * @param data    数据（对象）
     * @return 映射类对象
     */
    public static <E, T> E copy(T data, Class<E> toClass) {
        return mapperFacade.map(data, toClass);
    }

    /**
     * 映射集合（默认字段）
     *
     * @param toClass 映射类对象
     * @param data    数据（集合）
     * @return 映射类对象
     */
    public static <E, T> List<E> copyToList(Collection<T> data, Class<E> toClass) {
        return mapperFacade.mapAsList(data, toClass);
    }


    /**
     * 映射实体（自定义配置）
     *
     * @param toClass   映射类对象
     * @param data      数据（对象）
     * @param configMap 自定义配置
     * @return 映射类对象
     */
    public static <E, T> E copy(T data, Class<E> toClass, Map<String, String> configMap) {
        MapperFacade mapperFacade = getMapperFacade(toClass, data.getClass(), configMap);
        return mapperFacade.map(data, toClass);
    }

    /**
     * 映射集合（自定义配置）
     *
     * @param toClass   映射类
     * @param data      数据（集合）
     * @param configMap 自定义配置
     * @return 映射类对象
     */
    public static <E, T> List<E> copyToList(Collection<T> data, Class<E> toClass, Map<String, String> configMap) {
        T t = data.stream().findFirst().orElse(null);
        if (Objects.isNull(t)) {
            return null;
        }
        MapperFacade mapperFacade = getMapperFacade(toClass, t.getClass(), configMap);
        return mapperFacade.mapAsList(data, toClass);
    }

    /**
     * 获取自定义映射
     *
     * @param toClass   映射类
     * @param dataClass 数据映射类
     * @param configMap 自定义配置
     * @return 映射类对象
     */
    public static <E, T> MapperFacade getMapperFacade(Class<E> toClass, Class<T> dataClass, Map<String, String> configMap) {
        String mapKey = dataClass.getCanonicalName() + "_" + toClass.getCanonicalName();
        MapperFacade mapperFacade = CACHE_MAPPER_FACADE_MAP.get(mapKey);
        if (Objects.isNull(mapperFacade)) {
            MapperFactory factory = new DefaultMapperFactory.Builder().build();
            ClassMapBuilder<T, E> classMapBuilder = factory.classMap(dataClass, toClass);
            configMap.forEach(classMapBuilder::field);
            classMapBuilder.byDefault().register();
            mapperFacade = factory.getMapperFacade();
            CACHE_MAPPER_FACADE_MAP.put(mapKey, mapperFacade);
        }
        return mapperFacade;
    }

}
