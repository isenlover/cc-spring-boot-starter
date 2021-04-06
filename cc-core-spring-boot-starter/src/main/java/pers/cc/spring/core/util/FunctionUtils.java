package pers.cc.spring.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author chengce
 * @version 2021-03-18 19:23
 */
public class FunctionUtils {

  /**
   * 去重
   * @param keyExtractor
   * @param <T>
   * @return
   */
  public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
    Map<Object, Boolean> seen = new ConcurrentHashMap<>();
    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }
}
