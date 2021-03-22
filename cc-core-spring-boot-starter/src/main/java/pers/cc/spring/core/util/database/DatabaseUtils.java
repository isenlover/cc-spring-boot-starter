package pers.cc.spring.core.util.database;

/**
 * 数据库工具类
 *
 * @author chengce
 * @version 2017-10-18 20:39
 */
public class DatabaseUtils {
    public static String likeAll(String pattern) {
        return "%" + pattern + "%";
    }

    public static String likePrevious(String pattern) {
        return "%" + pattern;
    }

    public static String likeNext(String pattern) {
        return pattern + "%";
    }
}
