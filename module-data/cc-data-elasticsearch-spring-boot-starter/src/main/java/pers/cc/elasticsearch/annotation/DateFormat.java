package pers.cc.elasticsearch.annotation;

/**
 * 日期类型
 *
 * @author chengce
 * @version 2018-07-06 16:31
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-date-format.html"></a>
 */
public enum DateFormat {
    date_optional_time,
    /**
     * 时间戳 毫秒
     */
    epoch_millis,
    epoch_second,
    custom,
    /**
     * yyyyMMdd
     */
    basic_date,
    /**
     * yyyyMMdd'T'HHmmss.SSSZ
     */
    basic_date_time,
    /**
     * yyyyMMdd'T'HHmmssZ
     */
    basic_date_time_no_millis,
    /**
     * yyyyDDD
     */
    basic_ordinal_date,
    /**
     * yyyyDDD'T'HHmmss.SSSZ
     */
    basic_ordinal_date_time,
    /**
     * yyyyDDD'T'HHmmssZ
     */
    basic_ordinal_date_time_no_millis,
    /**
     * HHmmss.SSSZ
     */
    basic_time,
    /**
     * HHmmssZ
     */
    basic_time_no_millis,
    /**
     * 'T'HHmmss.SSSZ
     */
    basic_t_time,
    /**
     * 'T'HHmmssZ
     */
    basic_t_time_no_millis,
    /**
     * xxxx'W'wwe
     */
    basic_week_date,
    /**
     * xxxx'W'wwe'T'HHmmss.SSSZ
     */
    basic_week_date_time,
    /**
     * xxxx'W'wwe'T'HHmmssZ
     */
    basic_week_date_time_no_millis,
    /**
     * yyyy-MM-dd
     */
    date,
    /**
     * yyyy-MM-dd'T'HH
     */
    date_hour,
    /**
     * yyyy-MM-dd'T'HH:mm
     */
    date_hour_minute,
    /**
     * yyyy-MM-dd'T'HH:mm:ss
     */
    date_hour_minute_second,
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    date_hour_minute_second_fraction,
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    date_hour_minute_second_millis,
    /**
     * yyyy-MM-dd'T'HH:mm:ss.SSSZZ
     */
    date_time,
    /**
     * yyyy-MM-dd'T'HH:mm:ssZZ
     */
    date_time_no_millis,
    /**
     * HH
     */
    hour,
    /**
     * HH:mm
     */
    hour_minute,
    /**
     * HH:mm:ss
     */
    hour_minute_second,
    /**
     * HH:mm:ss.SSS
     */
    hour_minute_second_fraction,
    /**
     * HH:mm:ss.SSS
     */
    hour_minute_second_millis,
    /**
     * yyyy-DDD
     */
    ordinal_date,
    /**
     * yyyy-DDD'T'HH:mm:ss.SSSZZ
     */
    ordinal_date_time,
    /**
     * yyyy-DDD'T'HH:mm:ssZZ
     */
    ordinal_date_time_no_millis,
    /**
     * HH:mm:ss.SSSZZ
     */
    time,
    /**
     * HH:mm:ssZZ
     */
    time_no_millis,
    /**
     * 'T'HH:mm:ss.SSSZZ
     */
    t_time,
    /**
     * 'T'HH:mm:ssZZ
     */
    t_time_no_millis,
    /**
     * xxxx-'W'ww-e
     */
    week_date,
    /**
     * 'T'HH:mm:ss.SSSZZ
     */
    week_date_time,
    /**
     * 'T'HH:mm:ssZZ
     */
    weekDateTimeNoMillis,
    /**
     * xxxx
     */
    week_year,
    /**
     * xxxx-'W'ww
     */
    weekyearWeek,
    /**
     * xxxx-'W'ww-e
     */
    weekyearWeekDay,
    /**
     * yyyy
     */
    year,
    /**
     * yyyy-MM
     */
    year_month,
    /**
     * yyyy-MM-dd
     */
    year_month_day,
    /**
     * yyyy年
     */
    custom_year,
    /**
     * yyyy年MM月
     */
    custom_year_month,
    /**
     * yyyy年MM月dd日
     */
    custom_date,
    /**
     * yyyy年MM月dd日 hh:mm:ss
     */
    custom_date_time,;

    private DateFormat() {
    }
}
