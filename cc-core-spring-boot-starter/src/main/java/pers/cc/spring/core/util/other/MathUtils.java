package pers.cc.spring.core.util.other;

import lombok.Data;
import net.logstash.logback.encoder.org.apache.commons.lang.ArrayUtils;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.CommonUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * 数学/计算类等等工具
 *
 * @author chengce
 * @version 2018-01-08 00:08
 */
public class MathUtils {
  private static String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"
      , "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G"
      , "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

  private static String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

  public static enum NonceType {
    /**
     * 字母
     */
    CHAR,
    /**
     * 数字
     */
    NUMBER,
    /**
     * 数字与字母混合，默认
     */
    MIXIN
  }

  /**
   * 获取32位不带 - 的UUID
   *
   * @return UUID
   */
  public static String getSimpleUUID() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

  /**
   * 浮点数保留几位小数
   *
   * @param data  totalYData
   * @param place 几位
   * @return 结果
   */
  public static String getDoubleString(double data, int place, String... suffix) {
    return String.format("%." + place + "f", data) + (suffix != null ? Arrays.toString(suffix)
        .replace("[", "")
        .replace("]", "") : "");
  }

  /**
   * 浮点数保留几位小数
   *
   * @param data  totalYData
   * @param place 几位
   * @return 结果
   */
  public static double getDouble(double data, int place) {
    // 可能会丢失精度
    String result = String.format("%." + place + "f", data);
//        String[] temps = result.split(".");
//        if (temps.length > 1) {
//            result = temps[0] + "." + temps[1].substring(0, place);
//        }
    return new BigDecimal(result).doubleValue();
  }

  public static double getDouble(AtomicInteger x, AtomicInteger y, int place) {
    return getDouble((double) x.get() / y.get(), place);
  }

  public static double getDouble(int x, int y, int place) {
    return getDouble((double) x / Math.max(1, y), place);
  }

  public static double getDouble(double x, double y, int place) {
    return getDouble(x / Math.max(1, y), place);
  }

  public static double getDouble(double x, int y, int place) {
    return getDouble(x / Math.max(1, y), place);
  }

  public static double getPercent(Object x, Object y, int place) {
    long yValue = Long.parseLong(String.valueOf(y));
    if (yValue == 0) {
      yValue = 1;
    }
    return getDouble(Long.parseLong(String.valueOf(x)) * 100.0 / yValue, place);
  }

  /**
   * 从0(包含)开始到max(不包含)区间 返回一个随机数
   *
   * @param max 最大值
   * @return 随机数-数字
   */
  public static int getRandomRange(int max) {
    Random random = new Random();
    return random.nextInt(max);
  }

  /**
   * 从min(包含)开始到max(不包含)区间 返回一个随机数
   *
   * @param min 最小值
   * @param max 最大值
   * @return 随机数-数字
   */
  public static int getRandomRange(int min, int max) {
    Random random = new Random();
    return random.nextInt(max) + min;
  }

  /**
   * 获取一组数字随机数
   *
   * @param min    最小值
   * @param max    最大值
   * @param count  数量
   * @param repeat 是否允许重复
   * @return 随机数
   */
  public static List<Integer> getRandomList(int min, int max, int count, boolean repeat) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      int random = getRandomRange(min, max);
      while (!repeat && list.contains(random)) {
        random = getRandomRange(min, max);
      }
      list.add(random);
    }
    return list;
  }

  /**
   * 获取一组不会重复的从0开始的数字随机数
   *
   * @param max   最大值
   * @param count 数量
   * @return 随机数
   */
  public static List<Integer> getRandomList(int max, int count) {
    return getRandomList(0, max, count, false);
  }

  /**
   * 获取随机字符串（包含小、大写字母和数字）
   *
   * @param length 要获取的长度
   * @return 随机字符串
   */
  public static String getRandom(int length, NonceType nonceType) {
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    String[] nonce;
    switch (nonceType) {
      case NUMBER: {
        nonce = numbers;
        break;
      }
      case CHAR: {
        nonce = chars;
        break;
      }
      default: {
        nonce = (String[]) ArrayUtils.addAll(numbers, chars);
        break;
      }
    }
    int maxLength = nonce.length;
    final String[] finalNonce = nonce;
    Stream.iterate(1, i -> i++)
        .limit(length)
        .forEach(s -> stringBuilder.append(finalNonce[random.nextInt(maxLength)]));
    return stringBuilder.toString();
  }

  public static int getFactorial(int n) {
    if (n < 0) {
      throw new BaseRuntimeException("负数没有阶乘");
    }
    if (n == 0) {
      return 1;
    } else {
      return n * getFactorial(n - 1);
    }
  }

  /**
   * 获取泊松结果
   * P(N(t)=n)=((λt)^n e^(-λt))/n!
   *
   * @param λ 变量
   * @param n 期望值
   * @param t 时间
   * @return 泊松结果
   * @link <a href="http://www.ruanyifeng.com/blog/2015/06/poisson-distribution.html"></a>
   */
  public static double getPoisson(double λ, int n, double t) {
    int factorialValue = getFactorial(n);
    double value = λ * t;
    return Math.pow(value, n) * Math.exp(-λ * t) / factorialValue;
  }

  /**
   * 获取泊松结果
   * P(N(t)=n)=((λt)^n e^(-λt))/n!
   *
   * @param λ    变量
   * @param n    期望值
   * @param t    时间
   * @param keep 保留小数点后几位
   * @return 泊松结果
   * @link <a href="http://www.ruanyifeng.com/blog/2015/06/poisson-distribution.html"></a>
   */
  public static double getPoisson(double λ, int n, double t, int keep) {
    return MathUtils.getDouble(getPoisson(λ, n, t), keep);
  }

  /**
   * 求取标准差和平均数
   *
   * @param list
   * @return
   */
  public static <T> StandardDeviationResult getStandardDeviation(List<T> list) {
    StandardDeviationResult result = new StandardDeviationResult();
    double average = list.stream().mapToDouble(value -> Double.parseDouble(value.toString())).average().orElse(-1);
    result.setAverage(average);
    double standardDeviation = Math.sqrt(list.stream().mapToDouble(value -> Math.pow(Double.parseDouble(value.toString()) - average, 2)).sum() / list.size());
    result.setStandardDeviation(standardDeviation);
    return result;
  }

  public static <T> StandardDeviationResult removeAbnormalValue(List<T> list) {
    StandardDeviationResult result = getStandardDeviation(list);
    list.removeIf(value -> Math.abs(Double.parseDouble(value.toString()) - result.getAverage()) > result.getStandardDeviation() * 2);
    return getStandardDeviation(list);
  }

  public static double getAverage(List<Double> list) {
    return list.stream().mapToDouble(value -> value).average().orElse(0);
  }

  @Data
  public static class StandardDeviationResult {
    private double standardDeviation;

    private double average;
  }

  /**
   * @param n
   * @param x 底数
   * @return
   */
  public static double log(double n, double x) {
    return Math.log(n) / Math.log(x);
  }

  public static double log(double n, double x, int place) {
    if (n == 0 || x <= 0 || x == 1) {
      return 0;
    }
    return MathUtils.getDouble(Math.log(n) / Math.log(x), place);
  }

  @Data
  public static class RangeAnalysisResult {
    private double min;

    private double max;
  }

  public static Message<RangeAnalysisResult> getRange(String rangeString) {
    if (CommonUtils.isEmpty(rangeString) || !rangeString.contains("-")) {
      return Message.<RangeAnalysisResult>failed().build();
    }
    String[] ranges = rangeString.split("-");
    RangeAnalysisResult rangeAnalysisResult = new RangeAnalysisResult();
    if (ranges.length == 2) {
      double min = Double.parseDouble(ranges[0]);
      double max = Double.parseDouble(ranges[1]);
      rangeAnalysisResult.setMin(min);
      rangeAnalysisResult.setMax(max);
      return Message.ok(rangeAnalysisResult);
    } else if (ranges.length == 3) {
      double min = Double.parseDouble("-" + ranges[1]);
      double max = Double.parseDouble(ranges[2]);
      rangeAnalysisResult.setMin(min);
      rangeAnalysisResult.setMax(max);
      return Message.ok(rangeAnalysisResult);
    }
    return Message.<RangeAnalysisResult>failed().build();
  }
}
