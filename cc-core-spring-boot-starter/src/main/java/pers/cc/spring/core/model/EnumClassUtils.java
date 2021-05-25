package pers.cc.spring.core.model;

import lombok.extern.slf4j.Slf4j;
import pers.cc.spring.core.util.other.ClassUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chengce
 * @version 2021-03-31 14:49
 */
@Slf4j
public class EnumClassUtils {

  public static List<EnumClassVO<Void>> generatorNoSource(List originList, String labelKey) {
    return generator(originList, labelKey, "id", null);
  }

  public static <T> List<EnumClassVO<T>> generator(List<T> originList, String labelKey) {
    return generator(originList, labelKey, "id", null);
  }

  public static <T> List<EnumClassVO<T>> generator(List<T> originList, String labelKey, boolean hasData) {
    return generator(originList, labelKey, "id", null, hasData);
  }

  public static <T> List<EnumClassVO<T>> generator(List<T> originList, String labelKey, String valueKey) {
    return generator(originList, labelKey, valueKey, null);
  }

  public static <T> List<EnumClassVO<T>> generator(List<T> originList, String labelKey, String valueKey, String childrenKey) {
    return generator(originList, labelKey, valueKey, childrenKey, false);
  }

  public static <T> List<EnumClassVO<T>> generator(List<T> originList, String labelKey, String valueKey, String childrenKey, boolean hasData) {
    return generator(originList, labelKey, valueKey, childrenKey, hasData, true);
  }

  public static <T> List<EnumClassVO<T>> generator(List<T> originList, String labelKey, String valueKey, String childrenKey, boolean hasData, boolean distinct) {
    Stream<EnumClassVO<T>> stream = originList.stream().map(t -> {
      EnumClassVO.EnumClassVOBuilder<T> builder = EnumClassVO.<T>builder()
          .label(ClassUtils.getValue(t, labelKey))
          .value(ClassUtils.getValue(t, valueKey));
      if (hasData) {
        builder.data(t);
      }
      if (childrenKey != null) {
        List<T> children = ClassUtils.getValue(t, childrenKey);
        if (children != null && children.size() > 0) {
          builder.children(generator(children, labelKey, valueKey, childrenKey, hasData));
        }
      }
      return builder.build();
    });
    if (distinct) {
      return stream
          .collect(Collectors.collectingAndThen(
              Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(EnumClassVO::getValue))),
              ArrayList::new
          ));
    }
    return stream.collect(Collectors.toList());
  }
}
