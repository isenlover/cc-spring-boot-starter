package pers.cc.spring.core.util.other;

import pers.cc.spring.core.exception.BaseRuntimeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类
 *
 * @author chengce
 * @version 2018-01-08 21:45
 */
public class DateUtils {
  public static final String dateFormat = "yyyy-MM-dd";
  public static final String dayFormat = "MM-dd";
  public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
  public static final String timeFormat = "HH:mm:ss";
  public static final String dateTimeMsFormat = "yyyy-MM-dd HH:mm:ss:SSS";

  /**
   * 获取以现在时间为基准之后或之前天数的时间
   *
   * @param day 天数，正负
   * @return 格式化后的结果
   */
  public static String getAfterOrBeforeDay(int day) {
    return getAfterOrBeforeDay(day, dateFormat);
  }

  /**
   * 获取以现在时间为基准之后或之前天数的时间
   *
   * @param day 天数，正负
   * @return 格式化后的结果
   */
  public static Date getDateAfterOrBeforeDay(int day) {
    return getDateAfterOrBeforeDay(day, dateFormat);
  }

  /**
   * 获取以现在时间为基准之后或之前天数的时间
   *
   * @param day    天数，正负
   * @param format 格式化
   * @return 格式化后的结果
   */
  public static String getAfterOrBeforeDay(int day, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_WEEK, day);
    return new SimpleDateFormat(format).format(calendar.getTime());
  }

  public static Date getDateAfterOrBeforeDay(Date date, int day) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_WEEK, day);
    return calendar.getTime();
  }

  /**
   * 获取以现在时间为基准之后或之前天数的时间
   *
   * @param day    天数，正负
   * @param format 格式化
   * @return 格式化后的结果
   */
  public static Date getDateAfterOrBeforeDay(int day, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_WEEK, day);
    return calendar.getTime();
  }

  public static Date getDateAfterOrBeforeMonth(Date date, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, month);
    return calendar.getTime();
  }

  /**
   * 获取改变后的日期（月）
   *
   * @param date   需要改变的日期
   * @param month  月份 正负加减
   * @param format 格式化
   * @return 格式化后结果
   */
  public static String getAdjustDateWithMonth(Date date, int month, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, month);
    return new SimpleDateFormat(format).format(calendar.getTime());
  }

  /**
   * 获取以现在时间为基准之后或之前小时的时间
   *
   * @param hour 天数，正负
   * @return 格式化后的结果
   */
  public static String getAfterOrBeforeHour(int hour) {
    return getAfterOrBeforeHourString(hour, dateFormat);
  }

  public static Date getDateAfterOrBeforeHour(int hour) {
    return getAfterOrBeforeHour(hour, dateFormat);
  }

  public static Date getDateAfterOrBeforeMonth(int month) {
    return getDateAfterOrBeforeMonth(new Date(), month);
  }

  public static Date getDateAfterOrBeforeHour(Date date, int month) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR, month);
    return calendar.getTime();
  }

  /**
   * 获取以现在时间为基准之后或之前小时的时间
   *
   * @param hour   天数，正负
   * @param format 格式化
   * @return 格式化后的结果
   */
  public static String getAfterOrBeforeHourString(int hour, String format) {
    return new SimpleDateFormat(format).format(getAfterOrBeforeHour(hour, format));
  }

  public static Date getAfterOrBeforeHour(int hour, String format) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR, hour);
    return calendar.getTime();
  }


  /**
   * 获取当前时间（24小时制）
   *
   * @return 时间字符串
   */
  public static String getCurrentTime() {
    return getCurrentTime(dateTimeFormat);
  }

  /**
   * 获取当前时间
   *
   * @param format 格式化
   * @return 时间字符串
   */
  public static String getCurrentTime(String format) {
    return getCurrentTime(format, Locale.getDefault(Locale.Category.FORMAT));
  }

  /**
   * 获取当前时间
   *
   * @param format 格式化
   * @return 时间字符串
   */
  public static String getCurrentTime(String format, Locale locale) {
    return new SimpleDateFormat(format, locale).format(new Date());
  }

  /**
   * 获取当月第一天
   *
   * @return 当月第一天
   */
  public static String getCurrentMonthFirstDay() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // 获取前月的第一天
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, 0);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    return format.format(calendar.getTime());
  }


  /**
   * 获取当月最后一天
   *
   * @return 当月最后一天
   */
  public static String getCurrentMonthLastDay() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // 获取前月的第一天
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, 1);
    calendar.set(Calendar.DAY_OF_MONTH, 0);
    return format.format(calendar.getTime());
  }

  /**
   * 计算两个日期之间的差值
   *
   * @param from 日期1 格式必须为 yyyy-MM-dd hh:mm:ss
   * @param to   日期2 格式必须为 yyyy-MM-dd hh:mm:ss
   * @return 返回时间戳
   */
  public static long getTimestampBetweenDateTime(String from, String to) throws ParseException {
    long time = getTimestamp(from, dateTimeFormat) - getTimestamp(to, dateTimeFormat);
    return Math.max(time, 0);
  }

  /**
   * 计算两个日期之间的差值
   *
   * @param from 日期1
   * @param to   日期2
   * @return 返回时间戳
   */
  public static long getTimestampBetweenDateTime(Date from, Date to) {
    return Math.abs(to.getTime() - from.getTime());
  }

  /**
   * 计算从现在开始到传入日期还需要的 毫秒数
   *
   * @param to 格式必须为 yyyy-MM-dd hh:mm:ss
   * @return 返回时间戳
   */
  public static long getTimestampFromNow(String to) throws ParseException {
    long time = getTimestamp(to, dateTimeFormat) - getTimestamp(TimeUnit.MILLISECONDS);
    return Math.max(time, 0);
  }

  /**
   * 计算从现在开始到传入日期还需要的 毫秒数
   *
   * @param to 日期
   * @return 返回时间戳
   */
  public static long getTimestampFromNow(Date to) throws ParseException {
    long time = to.getTime() - getTimestamp(TimeUnit.MILLISECONDS);
    return Math.max(time, 0);
  }

  /**
   * 计算传入日期到现在经历的 毫秒数
   *
   * @param from 日期
   * @return 返回时间戳
   */
  public static long getTimestampToNow(Date from) {
    long time = getTimestamp(TimeUnit.MILLISECONDS) - from.getTime();
    return Math.max(time, 0);
  }

  /**
   * 获取日期的时间戳
   *
   * @param dateTime 日期
   * @return 时间戳
   * @throws ParseException 转换异常
   */
  public static Long getTimestamp(String dateTime, String format) throws ParseException {
    return new SimpleDateFormat(format).parse(dateTime).getTime();
  }

  /**
   * 获取日期的时间戳
   *
   * @param dateTime 日期 yyyy-MM-dd
   * @return 时间戳
   * @throws ParseException 转换异常
   */
  public static Long getTimestamp(String dateTime) throws ParseException {
    return getTimestamp(dateTime, dateFormat);
  }

  /**
   * 获取当前时间戳
   *
   * @param timeUnit 可以获取秒级（10位）
   * @return 时间戳
   */
  public static Long getTimestamp(TimeUnit timeUnit) {
    if (timeUnit == TimeUnit.SECONDS) {
      return Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10));
    } else {
      return System.currentTimeMillis();
    }
  }

  /**
   * 获取当前时间戳
   *
   * @return 时间戳 毫秒
   */
  public static Long getTimestamp() {
    return System.currentTimeMillis();
  }

  /**
   * 把字符串格式的日期转成日期
   *
   * @param dateTime 日期字符串
   * @return 日期
   */
  public static Date getDate(String dateTime, String format) {
    try {
      return new SimpleDateFormat(format).parse(dateTime);
    } catch (ParseException e) {
      throw new BaseRuntimeException("日期parse出错");
    }
  }

  /**
   * 把字符串格式的日期转成日期
   *
   * @param dateTime 日期字符串 yyyy-MM-dd
   * @return 日期
   */
  public static Date getDate(String dateTime) {
    return getDate(dateTime, dateFormat);
  }

  /**
   * 把日期转为字符串
   *
   * @param date   日期字符串
   * @param format 格式化
   * @return 日期
   */
  public static String getString(Date date, String format) {
    return new SimpleDateFormat(format).format(date);
  }

  /**
   * 把日期转为字符串
   *
   * @param date 日期字符串 yyyy-MM-dd
   * @return 日期
   */
  public static String getString(Date date) {
    return getString(date, dateFormat);
  }

  /**
   * 返回当天日期  yyyy-MM-dd
   *
   * @return 日期
   */
  public static Date getCurrentDay() {
    return new Date();
  }

  public static String getCurrentDayString() {
    return getCurrentDayString(dateFormat);
  }

  public static String getCurrentDayString(String dateFormat) {
    return getString(new Date(), dateFormat);
  }

  public static boolean isToday(Date date) {
    if (date == null) {
      return false;
    }
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    String param = sdf.format(date);
    String now = sdf.format(new Date());
    return param.equals(now);
  }

  public static Date getTodayAtMidnight() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
        0, 0, 0);
    return calendar.getTime();
  }

  public static Date getDayAtMidnight(int afterOrBeforeDays) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
        0, 0, 0);
    return new Date(calendar.getTime().getTime() + afterOrBeforeDays * 24 * 3600 * 1000);
  }

  public static Date getToday235959() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
        23, 59, 59);
    return calendar.getTime();
  }

  public static Date getDay235959(int afterOrBeforeDays) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
        23, 59, 59);
    return new Date(calendar.getTime().getTime() + afterOrBeforeDays * 24 * 3600 * 1000);
  }

  public static Date getRandomDate(String beginDate, String endDate, String dateFormat) {
    try {
      SimpleDateFormat format = new SimpleDateFormat(dateFormat);
      Date start = format.parse(beginDate);
      Date end = format.parse(endDate);
      if (start.getTime() >= end.getTime()) {
        return null;
      }
      long date = randomDate(start.getTime(), end.getTime());
      return new Date(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static long randomDate(long begin, long end) {
    long rtn = begin + (long) (Math.random() * (end - begin));
    if (rtn == begin || rtn == end) {
      return randomDate(begin, end);
    }
    return rtn;
  }
}
