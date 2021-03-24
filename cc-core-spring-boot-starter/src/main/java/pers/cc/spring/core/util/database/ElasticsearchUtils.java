package pers.cc.spring.core.util.database;

import pers.cc.spring.core.util.other.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * es 工具类
 *
 * @author chengce
 * @version 2018-04-26 18:57
 */
public class ElasticsearchUtils {
  /**
   * 把list转为 es搜索的多词
   *
   * @param list 原始列表
   * @param key  键值
   * @return es多词
   * @throws NoSuchFieldException   异常
   * @throws IllegalAccessException 异常
   */
  public static <T> String getSearchText(List<T> list,
                                         String key) throws NoSuchFieldException, IllegalAccessException {
    StringBuilder stringBuilder = new StringBuilder();

    for (Object o : list) {
      Field field = o.getClass().getDeclaredField(key);
      field.setAccessible(true);
      stringBuilder.append(field.get(o)).append(" ");
    }
    return StringUtils.removeLastCharacter(stringBuilder.toString());
  }
}
