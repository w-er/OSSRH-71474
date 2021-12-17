package com.wencoder.core.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * 基于JDK8 time包的时间工具类
 * <p>
 * Created by 王林 on 2021-01-29 13:01:14
 */
@SuppressWarnings("all")
public class DateUtil {

    public static final String STRING_MARK = "-";
    public static final String STRING_TIME_MARK = ":";

    /**
     * 默认时间格式: yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = Format.LONG_DATE_PATTERN_LINE.formatter;

    private DateUtil() {

    }

    /**
     * 默认时间格式 String 转 LocalDateTime
     *
     * @param timeStr 文本时间
     * @return LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * LocalDateTime 转 默认时间格式String
     *
     * @param localDateTime 转换时间
     * @return 文本时间
     */
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return DEFAULT_DATETIME_FORMATTER.format(localDateTime);
    }

    /**
     * 特定时间格式String 转 LocalDateTime
     *
     * @param timeStr 文本时间
     * @param format  格式
     * @return 时间
     */
    public static LocalDateTime stringToLocalDateTime(String timeStr, Format format) {
        return LocalDateTime.parse(timeStr, format.formatter);
    }

    /**
     * LocalDateTime 转 特定时间格式String
     *
     * @param localDateTime 时间
     * @param format        格式
     * @return 文本时间
     */
    public static String localDateTimeToString(LocalDateTime localDateTime, Format format) {
        return format.formatter.format(localDateTime);
    }

    /**
     * 特定时间格式String 转 LocalDate
     *
     * @param timeStr 文本时间
     * @param format  格式
     * @return 时间
     */
    public static LocalDate stringToLocalDate(String timeStr, Format format) {
        return LocalDate.parse(timeStr, format.formatter);
    }

    /**
     * LocalDate 转 特定时间格式String
     *
     * @param localDate 时间
     * @param format    格式
     * @return 文本时间
     */
    public static String localDateToString(LocalDate localDate, Format format) {
        return format.formatter.format(localDate);
    }

    /**
     * 特定时间格式String 转 LocalTime
     *
     * @param timeStr 文本时间
     * @param format  格式
     * @return 时间
     */
    public static LocalTime stringToLocalTime(String timeStr, Format format) {
        return LocalTime.parse(timeStr, format.formatter);
    }

    /**
     * LocalTime 转 特定时间格式String
     *
     * @param localTime 时间
     * @param format    格式
     * @return 文本时间
     */
    public static String localTimeToString(LocalTime localTime, Format format) {
        return format.formatter.format(localTime);
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime local 时间
     * @return date 时间
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date date 时间
     * @return local 时间
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    /**
     * 毫秒数 转 LocalDateTime
     *
     * @param millis 毫秒数
     * @return 时间
     */
    public static LocalDateTime millisToLocalDateTime(Long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    /**
     * LocalDateTime 转 毫秒数
     *
     * @param localDateTime 时间
     * @return 毫秒数
     */
    public static Long localDateTimeToMillis(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 毫秒数 转 LocalDate
     *
     * @param millis 毫秒数
     * @return LocalDate
     */
    public static LocalDate millisToLocalDate(Long millis) {
        return millisToLocalDateTime(millis).toLocalDate();
    }

    /**
     * LocalDate 转 毫秒数
     *
     * @param localDate LocalDate
     * @return 毫秒数
     */
    public static Long localDateToMillis(LocalDate localDate) {
        return localDateTimeToMillis(LocalDateTime.of(localDate, LocalTime.MIN));
    }

    /**
     * 毫秒数 转 LocalTime
     *
     * @param millis 毫秒数
     * @return LocalTime
     */
    public static LocalTime millisToLocalTime(Long millis) {
        return millisToLocalDateTime(millis).toLocalTime();
    }

    /**
     * 获取当前时间默认时间格式String
     *
     * @return 文本时间
     */
    public static String getCurrentTimeString() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间特定时间格式String
     *
     * @param format 格式
     * @return 格式化文本时间
     */
    public static String getCurrentTimeString(Format format) {
        return format.formatter.format(LocalDateTime.now());
    }

    /**
     * 获取某个时间点当天的开始时间 '00:00'
     *
     * @param localDateTime 指定时间
     * @return 当天开始时间
     */
    public static LocalDateTime getStartTime(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    }

    /**
     * 获取某个时间点当天的结束时间 '23:59:59.999999999'
     *
     * @param localDateTime 指定时间
     * @return 当天结束时间
     */
    public static LocalDateTime getEndTime(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
    }

    /**
     * Date 转 特定时间格式String
     *
     * @param date   Date
     * @param format 格式
     * @return 格式化文本时间
     */
    public static String dateToString(Date date, Format format) {
        return localDateTimeToString(dateToLocalDateTime(date), format);
    }

    /**
     * 特定时间格式String 转 Date
     *
     * @param timeStr 文本时间
     * @param format  格式
     * @return Date
     */
    public static Date stringToDate(String timeStr, Format format) {
        return localDateTimeToDate(stringToLocalDateTime(timeStr, format));
    }

    /**
     * 毫秒数 转 特定时间格式String
     *
     * @param millis 毫秒数
     * @param format 格式
     * @return 格式化文本时间
     */
    public static String millisToString(Long millis, Format format) {
        LocalDateTime localDateTime = millisToLocalDateTime(millis);
        return localDateTimeToString(localDateTime, format);
    }

    /**
     * 特定时间格式String 转 毫秒数
     *
     * @param timeStr 文本时间
     * @param format  格式
     * @return 毫秒数
     */
    public static Long stringToMillis(String timeStr, Format format) {
        LocalDateTime localDateTime = stringToLocalDateTime(timeStr, format);
        return localDateTimeToMillis(localDateTime);
    }

    /**
     * LocalDateTime 转 秒
     *
     * @param localDateTime LocalDateTime
     * @return 秒
     */
    public static Long localDateTimeToSecond(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
    }

    /**
     * 秒 转 LocalDateTime
     *
     * @param second 秒
     * @return LocalDateTime
     */
    public static LocalDateTime secondToLocalDateTime(Long second) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(second), ZoneId.systemDefault());
    }

    /**
     * 日期加上一个数，根据field不同加不同值,field为ChronoUnit.*
     *
     * @param localDateTime LocalDateTime
     * @param number        加数值
     * @param field         单位
     * @return 结果时间
     */
    public static LocalDateTime plus(LocalDateTime localDateTime, Long number, TemporalUnit field) {
        return localDateTime.plus(number, field);
    }

    /**
     * 日期减去一个数，根据field不同减不同值，field参数为ChronoUnit.*
     *
     * @param localDateTime LocalDateTime
     * @param number        减数值
     * @param field         单位
     * @return 结果时间
     */
    public static LocalDateTime minus(LocalDateTime localDateTime, Long number, TemporalUnit field) {
        return localDateTime.minus(number, field);
    }

    /**
     * 获取两个日期的差 field参数为ChronoUnit.*
     *
     * @param startTime 开始
     * @param endTime   结束
     * @param field     单位
     * @return 结果时间
     */
    public static Long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return (long) period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12L + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * LocalDate 反序列化器
     * 接受 String 类型 yyyy-MM-dd 格式
     *
     * @return JsonDeserializer
     */
    public static JsonDeserializer<LocalDate> localDateDeserializer() {
        return new JsonDeserializer<LocalDate>() {
            @Override
            public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String time = jsonParser.getText();
                if (time.contains(STRING_MARK)) {
                    return stringToLocalDate(time, Format.SHORT_DATE_PATTERN_LINE);
                }
                return millisToLocalDate(Long.parseLong(time));
            }
        };
    }

    /**
     * LocalTime 反序列化器
     * 接受 String 类型 HH:mm:ss 格式
     *
     * @return JsonDeserializer
     */
    public static JsonDeserializer<LocalTime> localTimeDeserializer() {
        JsonDeserializer<LocalTime> jsonDeserializer = new JsonDeserializer<LocalTime>() {
            @Override
            public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException {
                String time = jsonParser.getText();
                if (time.contains(STRING_TIME_MARK)) {
                    return stringToLocalTime(time, Format.TIME_PATTERN);
                }
                return millisToLocalTime(Long.parseLong(time));
            }
        };
        return jsonDeserializer;
    }

    /**
     * LocalDateTime 反序列化器
     * 接受 String 默认类型
     *
     * @return JsonDeserializer
     */
    public static JsonDeserializer<LocalDateTime> localDateTimeDeserializer() {
        return new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String time = jsonParser.getText();
                if (time.contains(STRING_MARK)) {
                    return stringToLocalDateTime(time, Format.LONG_DATE_PATTERN_LINE);
                }
                return millisToLocalDateTime(Long.parseLong(time));
            }
        };
    }

    /**
     * 时间支持处理类型
     */
    public enum Format {

        /**
         * 短时间格式 只保留年月
         */
        SHORT_YEAR_MONTH_PATTERN_LINE("yyyy-MM"),
        SHORT_YEAR_MONTH_PATTERN_SLASH("yyyy/MM"),
        SHORT_YEAR_MONTH_PATTERN_DOUBLE_SLASH("yyyy\\MM"),
        SHORT_YEAR_MONTH_PATTERN_NONE("yyyyMM"),

        /**
         * 短时间格式
         */
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

        /**
         * 长时间格式
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),

        /**
         * 长时间格式 带毫秒
         */
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS"),

        /**
         * 时分秒时间格式
         */
        TIME_PATTERN("HH:mm:ss"),
        TIME_PATTERN_WITH_MILSEC("HH:mm:ss.SSS"),
        SHORT_TIME_PATTERN("HHmmss"),
        SHORT_TIME_PATTERN_WITH_MILSEC("HHmmss.SSS"),

        /**
         * 特殊格式 根据需求自定义
         */
        LONG_DATE_PATTERN_WITH_MINUTE("yyyyMMddHHmm"),
        LONG_DATE_PATTERN_WITH_SECOND("yyyyMMddHHmmss");

        public transient DateTimeFormatter formatter;

        Format(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }
}
