package pers.cc.spring.core.util.other;

import pers.cc.spring.core.util.CommonUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author chengce
 * @version 2018-01-10 22:38
 */
public class StringUtils {
  /**
   * 去掉末尾字符
   *
   * @param str 字符串
   * @return 字符串
   */
  public static String removeLastCharacter(String str) {
    if (CommonUtils.isEmpty(str)) return "";
    return str.substring(0, str.length() - 1);
  }

  /**
   * 分割字符串
   *
   * @param str       字符串
   * @param separator 分隔符
   * @return 字符串数组
   */
  public static String[] split(String str, String separator) {
    if (CommonUtils.isEmpty(str)) return null;
    return str.split(separator);
  }

  /**
   * 字符串转list
   *
   * @param str       字符串
   * @param separator 分隔符
   * @return list
   */
  public static List<String> asList(String str, String separator) {
    String[] ret = split(str, separator);
    if (ret == null) {
      return null;
    }
    return Arrays.asList(ret);
  }

  /**
   * 首字母小写
   *
   * @param str 字符串
   * @return 字符串
   */
  public static String toLowerCaseFirst(String str) {
    String first = str.substring(0, 1).toLowerCase();
    return first + str.substring(1);
  }

  /**
   * 首字母大写
   *
   * @param str 字符串
   * @return 字符串
   */
  public static String toUpperCaseFirst(String str) {
    String first = str.substring(0, 1).toUpperCase();
    return first + str.substring(1);
  }

  /**
   * 字符串转Ascii
   *
   * @param value 字符串
   * @return Ascii
   */
  public static String stringToAscii(String value) {
    StringBuilder result = new StringBuilder();
    char[] chars = value.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (i != chars.length - 1) {
        result.append((int) chars[i]).append(",");
      } else {
        result.append((int) chars[i]);
      }
    }
    return result.toString();
  }

  /**
   * Ascii转字符串
   *
   * @param value Ascii
   * @return 字符串
   */
  public static String asciiToString(String value) {
    StringBuilder result = new StringBuilder();
    String[] chars = value.split(",");
    for (String aChar : chars) {
      result.append((char) Integer.parseInt(aChar));
    }
    return result.toString();
  }

  /**
   * 截取多余的字符串
   *
   * @param value     字符串
   * @param maxLength 最大长度
   * @return 截取后的字符串
   */
  public static String getSplitString(String value, int maxLength) {
    return value.length() > maxLength ? value.substring(0, maxLength) : value;
  }

  /**
   * 去掉所有空格
   *
   * @param value
   * @return
   */
  public static String getTrimString(String value) {
    return value.replace(" ", "");
  }

  public static String getBracketString(Object label) {
    return "(" + label + ")";
  }

  public static String getDivideString(Object label) {
    return "/" + label;
  }

  /**
   * 首字母大写
   *
   * @param str
   * @return
   */
  public static String getCaptureName(String str) {
    // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
    char[] cs = str.toCharArray();
    cs[0] -= 32;
    return String.valueOf(cs);
  }
}
