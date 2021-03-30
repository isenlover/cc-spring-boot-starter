package pers.cc.spring.core.util.other;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author chengce
 * @version 2017-12-30 20:21
 */
public class RegExpUtils {

  private String regExp;

  public static final String PATTERN_MOBILE_CHINA_MAINLAND = "^1[3|5|6|7|8|9]\\d{9}$";

  public static final String PATTERN_EMAIL = "\\w+@\\w+.\\w+";

  public static final String PATTERN_MOBILE_CHINA_MAINLAND_OR_EMAIL = "^(" + PATTERN_MOBILE_CHINA_MAINLAND + ")|(" + PATTERN_EMAIL + ")$";

  public static final String PATTERN_CHINESE_CHAR_NUMBER = "^[A-Za-z\\u0391-\\uFFE50-9]$";

  public static final String PATTERN_CHINESE_NAME = "^[\\u4e00-\\u9fa5]{2,5}$";

  public static final String PATTERN_CHAR_NUMBER = "^\\w{6,20}$";

  public static final String PATTERN_COMMON_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

  public static final String PATTERN_CODE_4 = "^\\d{4}$";

  public static final String PATTERN_NUMBER = "^\\d+$";

  public static final String PATTERN_IMAGE_CODE_4 = "^\\w{4}$";

  public static enum MatchType {
    /**
     * 正常匹配，默认值
     */
    normal,
    /**
     * 忽略大小写
     */
    ignore,
  }

  private RegExpUtils() {

  }

  private Pattern getPattern(MatchType matchType) {
    switch (matchType) {
      case ignore:
        return Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
      default:
        return Pattern.compile(regExp);
    }
  }

  public static RegExpUtils builder(String regExp) {
    RegExpUtils regExpUtil = new RegExpUtils();
    regExpUtil.regExp = regExp;
    return regExpUtil;
  }

  /**
   * 匹配正则表达式
   *
   * @param str 需要匹配的字符串
   * @return 匹配结果
   */
  public boolean match(String str) {
    return match(str, MatchType.normal);
  }

  /**
   * 匹配正则表达式
   *
   * @param str       需要匹配的字符串
   * @param matchType 匹配类型
   * @return 匹配结果
   */
  public boolean match(String str,
                       MatchType matchType) {
    Pattern pattern = getPattern(matchType);
    Matcher matcher = pattern.matcher(str);
    return matcher.matches();
  }

  /**
   * 匹配某个值
   *
   * @param str 字符串
   * @return 匹配结果
   */
  public Matcher find(String str) {
    return find(str, MatchType.normal);
  }

  /**
   * 匹配某些值
   *
   * @param str       字符串
   * @param matchType 匹配类型
   * @return 匹配结果
   */
  public Matcher find(String str,
                      MatchType matchType) {
    Pattern pattern = getPattern(matchType);
    return pattern.matcher(str);
  }

  /**
   * 匹配大陆手机号
   *
   * @param str 手机号
   * @return 匹配结果
   */
  public static boolean matchMobile(String str) {
    return RegExpUtils.builder(PATTERN_MOBILE_CHINA_MAINLAND).match(str);
  }

  /**
   * 匹配常用的 英文(大小写)/数字/下划线
   *
   * @param str       待匹配字符串
   * @param minLength 最小长度
   * @param maxLength 最大长度
   * @return 匹配结果
   */
  public static boolean matchNormalCharacter(String str,
                                             int minLength,
                                             int maxLength) {
    String regExp = "^[A-Za-z0-9_]{" + minLength + "," + maxLength + "}$";
    return RegExpUtils.builder(regExp).match(str);
  }

  /**
   * 匹配 英文 大小写
   *
   * @param str       待匹配字符串
   * @param minLength 最小长度
   * @param maxLength 最大长度
   * @return 匹配结果
   */
  public static boolean matchCharacter(String str,
                                       int minLength,
                                       int maxLength) {
    String regExp = "^[A-Za-z]{" + minLength + "," + maxLength + "}$";
    return RegExpUtils.builder(regExp).match(str);
  }


  /**
   * 匹配 中文、英文、数字
   *
   * @param str       待匹配字符串
   * @param minLength 最小长度
   * @param maxLength 最大长度
   * @return 匹配结果
   */
  public static boolean matchNormal(String str,
                                    int minLength,
                                    int maxLength) {
    String regExp = "^[A-Za-z\\u0391-\\uFFE50-9]{" + minLength + "," + maxLength + "}$";
    return RegExpUtils.builder(regExp).find(str).find();
  }

  /**
   * 模糊匹配 英文 大小写
   *
   * @param str       待匹配字符串
   * @param minLength 最小长度
   * @param maxLength 最大长度
   * @return 匹配结果
   */
  public static boolean matchFuzzyCharacter(String str,
                                            int minLength,
                                            int maxLength) {
    String regExp = "[A-Za-z]{" + minLength + "," + maxLength + "}";
    return RegExpUtils.builder(regExp).find(str).find();
  }

  /**
   * 模糊匹配 中文、英文、数字
   *
   * @param str       待匹配字符串
   * @param minLength 最小长度
   * @param maxLength 最大长度
   * @return 匹配结果
   */
  public static boolean matchFuzzyNormal(String str,
                                         int minLength,
                                         int maxLength) {
    String regExp = "[A-Za-z\\u0391-\\uFFE50-9]{" + minLength + "," + maxLength + "}";
    return RegExpUtils.builder(regExp).find(str).find();
  }

  /**
   * 匹配数字
   *
   * @param str       待匹配字符串
   * @param minLength 最小长度
   * @param maxLength 最大长度
   * @return 匹配结果
   */
  public static boolean matchNumber(String str,
                                    int minLength,
                                    int maxLength) {
    String regExp = "^\\d{" + minLength + "," + maxLength + "}$";
    return RegExpUtils.builder(regExp).match(str);
  }

  public static boolean matchNumber(Integer str,
                                    int minLength,
                                    int maxLength) {
    return matchNumber(String.valueOf(str), minLength, maxLength);
  }

  public static boolean matchNumber(Long str,
                                    int minLength,
                                    int maxLength) {
    return matchNumber(String.valueOf(str), minLength, maxLength);
  }

  /**
   * 判断是否为浮点数
   *
   * @param str 字符串
   * @return 是否为浮点数
   */
  public static boolean matchFloatNumber(String str) {
    String regExp = "^[0-9]+(.[0-9]+)?$";
    return RegExpUtils.builder(regExp).match(str);
  }

  /**
   * 匹配中文
   *
   * @param str       中文
   * @param minLength 最小长度
   * @param maxLength 最大长度
   * @return 匹配结果
   */
  public static boolean matchChinese(String str,
                                     int minLength,
                                     int maxLength) {
    String regExp = "^[\\u4e00-\\u9fa5]{" + minLength + "," + maxLength + "}$";
    return RegExpUtils.builder(regExp).match(str);
  }

  /**
   * 匹配中文
   *
   * @param str 中文
   * @return 匹配结果
   */
  public static boolean matchChinese(String str) {
    String regExp = "[\\u4e00-\\u9fa5]";
    return RegExpUtils.builder(regExp).match(str);
  }

  /**
   * 是否包含中文
   *
   * @param str 中文
   * @return 匹配结果
   */
  public static boolean existChinese(String str) {
    String regExp = "[\\u4e00-\\u9fa5]";
    return RegExpUtils.builder(regExp).find(str).find();
  }

  /**
   * 匹配4或6位验证码
   *
   * @param str 待匹配字符串
   * @return 匹配结果
   */
  public static boolean matchNumberCode(String str) {
    String regExp = "^\\d{4,6}$";
    return RegExpUtils.builder(regExp).match(str);
  }
}
