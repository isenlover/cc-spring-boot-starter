package pers.cc.spring.core.model;

import lombok.extern.slf4j.Slf4j;
import pers.cc.spring.core.util.other.ClassUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chengce
 * @version 2021-03-31 14:49
 */
@Slf4j
public class EnumClassUtils {
  public static <T> List<EnumClassVO> generator(List<T> originList, String labelKey, String valueKey) {
    return generator(originList, labelKey, valueKey, null);
  }

  public static <T> List<EnumClassVO> generator(List<T> originList, String labelKey, String valueKey, String childrenKey) {
    return originList.stream().map(t -> {
      EnumClassVO.EnumClassVOBuilder builder = EnumClassVO.builder()
          .label(ClassUtils.getValue(t, labelKey))
          .value(ClassUtils.getValue(t, valueKey));
      if (childrenKey != null) {
        List<T> children = ClassUtils.getValue(t, childrenKey);
        if (children != null && children.size() > 0) {
          builder.children(generator(children, labelKey, valueKey, childrenKey));
        }
      }
      return builder.build();
    })
        .collect(Collectors.toList());
  }
}
