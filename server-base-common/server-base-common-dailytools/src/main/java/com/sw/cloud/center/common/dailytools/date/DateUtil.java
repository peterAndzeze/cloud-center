package com.sw.cloud.center.common.dailytools.date;


import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期通用工具类
 */
public class DateUtil {
    private static final DateTimeFormatter  TIME_ONE= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter  TIME_TWO= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
    private static final DateTimeFormatter  TIME_FOUR=DateTimeFormatter.ofPattern("yyyy/MM/dd hh/mm/ss");
    private static final DateTimeFormatter  TIME_FIVE=DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss:SSS");
    private static final DateTimeFormatter  TIME_THREE= DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter  TIME_SIX=DateTimeFormatter.ofPattern( "yyyy/MM/dd");
    private static final DateTimeFormatter  TIME_SEVEN= DateTimeFormatter.ofPattern("yyyyMMdd");


    /**
     * 格式化日期
      * @param date 时间
     * @param pattern 时间格式
     * @return 字符串
     */
    public static String dateToString(LocalDateTime date, DateTimeFormatter pattern){
        String format = date.format(pattern);
        return format;
    }

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String dateToString(LocalDateTime date){
        String format = dateToString(date,TIME_ONE);
        return format;
    }

    /**
     * 字符串转时间
     * @param dateTime
     * @return
     */
    public static LocalDateTime stringToDate(String dateTime){
        LocalDateTime parse = LocalDateTime.parse(dateTime);
        return parse;
    }

    /**
     * 转数字
     * @param localDateTime
     * @return
     */
    public static Integer dateToInt(LocalDateTime localDateTime){
        String format = localDateTime.format(TIME_SEVEN);
        return Integer.valueOf(format);
    }



    /**
     * 字符串转时间
     * 2021-09-12T12:21:11  想去掉t 请在项目中设置全局时间属性
     * @param dateTime
     * @return
     */
    public static LocalDateTime stringToDate(String dateTime,DateTimeFormatter dateTimeFormatter){
        LocalDateTime parse = LocalDateTime.parse(dateTime,dateTimeFormatter);
        return parse;
    }






    /**
     * 获取一天最小时间
     * @return
     */
    public static String getDayMinTime(LocalDateTime localDateTime,DateTimeFormatter dateTimeFormatter){
        LocalDateTime min = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        return min.format(dateTimeFormatter);
    }

    /**
     * 获取一天最大时间
     * @return
     */
    public static String getDayMaxTime(LocalDateTime localDateTime,DateTimeFormatter dateTimeFormatter){
        LocalDateTime min = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
        return min.format(dateTimeFormatter);
    }

    /**
     * 计算两个时间相差时间
     * @param chronoUnit 单位
     * @return 时间差值
     */
    public static Long getDifferLongTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit chronoUnit){
        long until = startTime.until(endTime, chronoUnit);
        return until;
    }

     /**
     * 计算两个时间相差时间
     * @param chronoUnit 单位
     * @return 时间差值
     */
    public static Integer getDifferIntTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit chronoUnit){
        Long until = startTime.until(endTime, chronoUnit);
        return until.intValue();
    }

    /**
     * localDate 转date
     * @return
     */
    public static Date getDateNow(LocalDate localDate){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(zoneId);
        Date from = Date.from(zonedDateTime.toInstant());
        return from;
    }

    /**
     * localDate 转date
     * @return
     */
    public static LocalDate getLocalDate(Date date){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = date.toInstant().atZone(zoneId).toLocalDate();

        return localDate;
    }


    /**
     * localDate 转date
     * @return
     */
    public static LocalDateTime getLocalDateTime(Date date){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDate = date.toInstant().atZone(zoneId).toLocalDateTime();

        return localDate;
    }

    /**
     * 获取年
     * @param localDate
     * @return
     */
    public static int getYear(LocalDate localDate){
       return localDate.getYear();
    }

     /**
     * 获取月
     * @param localDate
     * @return
     */
    public static int getMonth(LocalDate localDate){
       return localDate.getMonth().getValue();
    }

    /**
     * 获取相差天数
     * @param start
     * @param end
     * @return
     */
    public static long getDiffDays(LocalDate start,LocalDate end){
        Duration between = Duration.between(start, end);
        return between.toDays();
    }

     /**
     * 获取相差小时
     * @param start
     * @param end
     * @return
     */
    public static long getDiffHours(LocalDate start,LocalDate end){
        Duration between = Duration.between(start, end);
        return between.toHours();
    }


     /**
     * 获取相差分钟
     * @param start
     * @param end
     * @return
     */
    public static long getDiffMinutes(LocalDate start,LocalDate end){
        Duration between = Duration.between(start, end);
        return between.toMinutes();
    }

    /**
     * 获取相差毫秒
     * @param start
     * @param end
     * @return
     */
    public static long getDiffMillis(LocalDate start,LocalDate end){
        Duration between = Duration.between(start, end);
        return between.toMillis();
    }

    /**
     * 增加天数
     * @param date
     * @param days
     * @return
     */
    public static LocalDate addDays(LocalDate date,int days){
        return date.plusDays(days);
    }

    /**
     * 前几天
     * @param date
     * @param days
     * @return
     */
    public static LocalDate beforeDays(LocalDate date,int days){
        return date.minusDays(days);
    }


    /**
     * 获取年
     * @return
     */
    public static int getYear(){
        return LocalDate.now().getYear();
    }



    public static String format(Date date) {
        return format(date, TIME_THREE.toString());
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        } else {
            return null;
        }
    }




    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }

    public static String getMonthFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        return format(cal.getTime());
    }

    public static String getMonthLastDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        cal.add(2, 1);
        cal.add(5, -1);
        return format(cal.getTime());
    }


}
