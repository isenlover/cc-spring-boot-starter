package pers.cc.spring.core.util.other;

import lombok.extern.slf4j.Slf4j;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.MessageCode;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static pers.cc.spring.core.util.CommonUtils.isNotEmpty;

/**
 * class操作工具
 *
 * @author chengce
 * @version 2017-04-30 17:01
 */
@Slf4j
public class ClassUtils {
  /**
   * 检查注解是否存在于类上
   *
   * @param object          类
   * @param annotationClass 注解
   * @param <T>             泛型
   * @return 是否存在
   */
  public static <T extends Annotation> boolean hasAnnotation(Class object, Class<T> annotationClass) {
    return getAnnotation(object, annotationClass) != null;
  }

  /**
   * 获取类上注解
   *
   * @param object          类
   * @param annotationClass 注解
   * @param <T>             泛型
   * @return 注解实例
   */
  public static <T extends Annotation> T getAnnotation(Class object, Class<T> annotationClass) {
    return (T) object.getAnnotation(annotationClass);
  }

  /**
   * map转bean
   *
   * @param map       map
   * @param beanClass bean class
   * @return bean
   */
  public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
    if (map == null)
      return null;
    T obj = null;
    try {
      obj = beanClass.newInstance();

      BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      for (PropertyDescriptor property : propertyDescriptors) {
        Method setter = property.getWriteMethod();
        if (setter != null) {
          setter.invoke(obj, map.get(property.getName()));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return obj;
  }

  public static <T> T mapWechatMapToObject(Map<String, String> map, Class<T> beanClass) {
    if (map == null)
      return null;
    T obj = null;
    try {
      obj = beanClass.newInstance();

      BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
      for (PropertyDescriptor property : propertyDescriptors) {
        Method setter = property.getWriteMethod();
        if (setter != null) {
          setter.invoke(obj, map.get(StringUtils.getCaptureName(property.getName())));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return obj;
  }

  /**
   * object转map
   *
   * @param obj bean
   * @return map
   */
  public static Map<String, Object> objectToMap(Object obj) {
    return objectToMap(obj, false);
  }

  public static Map<String, Object> objectToMap(Object obj, boolean convertEmpty) {
    if (obj == null) {
      return null;
    }

    Map<String, Object> map = new HashMap<>();

    Field[] declaredFields = obj.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      try {
        Object value = field.get(obj);
        if (convertEmpty) {
          map.put(field.getName(), value);
        } else if (value != null) {
          map.put(field.getName(), value);
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    return map;
  }

  /**
   * object转map 排序
   * 空值不会转换
   *
   * @param obj bean
   * @return map
   */
  public static SortedMap<Object, Object> objectToSortedMap(Object obj) {
    if (obj == null) {
      return null;
    }

    SortedMap<Object, Object> map = new TreeMap<>();

    Field[] declaredFields = obj.getClass().getDeclaredFields();
    for (Field field : declaredFields) {
      field.setAccessible(true);
      try {
        if (isNotEmpty(field.get(obj))) {
          if (!field.getName().equals("Package")) {
            map.put(field.getName(), field.get(obj));
          } else {
            map.put("package", field.get(obj));
          }
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    return map;
  }

  private static <T, V> void setFields(List<Field> fields,
                                       List<String> fieldNameList,
                                       T poData,
                                       Class<V> voClazz,
                                       V vo) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    for (Field field : fields) {
      //获取vo里面的写方法
      PropertyDescriptor voPropDesc = new PropertyDescriptor(field.getName(), voClazz == null ? vo.getClass() : voClazz);
      Method methodWrite = voPropDesc.getWriteMethod();
      //获取po里面的读方法
      //如果po里面存在Vo里面的字段值，就会自动copy
      if (fieldNameList.contains(field.getName())) {
        PropertyDescriptor tbPropDesc = new PropertyDescriptor(field.getName(), poData.getClass());
        Method methodRead = tbPropDesc.getReadMethod();
        Object invokeValue = methodRead.invoke(poData);
        if (field.getType().equals(String.class)) {
          if (invokeValue instanceof Integer || invokeValue instanceof Long || invokeValue instanceof Double) {
            methodWrite.invoke(vo, String.valueOf(invokeValue));
          } else {
            methodWrite.invoke(vo, invokeValue);
          }
        } else {
          try {
            methodWrite.invoke(vo, invokeValue);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  private static <T, V> void setFields(List<Field> fields,
                                       List<String> fieldNameList,
                                       T poData,
                                       V vo) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    setFields(fields, fieldNameList, poData, null, vo);
  }

  /**
   * 把po的字段转vo
   *
   * @param poData        po数据
   * @param targetVOClass voClass
   * @param <T>           po泛型
   * @param <V>           vo泛型
   * @return vo
   * @throws IntrospectionException    异常
   * @throws InstantiationException    异常
   * @throws IllegalAccessException    异常
   * @throws IllegalArgumentException  异常
   * @throws InvocationTargetException 异常
   */
  public static <T, V> V getClass(T poData,
                                  Class<V> targetVOClass,
                                  String... excludeFields) throws IntrospectionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    //获取vo的全部属性值
    List<Field> fields = getClassAllFields(targetVOClass, excludeFields);//获取所有域名
    //并创建一个vo对象
    V vo = targetVOClass.newInstance();

    //获取po的全部属性名
    List<String> fieldNameList = getClassAllFieldName(poData.getClass(), excludeFields);
    setFields(fields, fieldNameList, poData, targetVOClass, vo);
    return vo;
  }

  public static List<String> getClassAllFieldName(Class<?> clazz, String... excludeFields) {
    return getClassAllFields(clazz, excludeFields).stream().map(Field::getName).collect(Collectors.toList());
  }

  public static List<Field> getClassAllFields(Class<?> clazz, String... excludeFields) {
    List<Field> fieldList = new ArrayList<>();
    if (clazz.getSuperclass() != null) {
      fieldList.addAll(getClassAllFields(clazz.getSuperclass()));
    }
    Field[] fields = clazz.getDeclaredFields();
    fieldList.addAll(asList(fields));
    if (excludeFields != null) {
      fieldList = fieldList.stream()
          .filter(field -> Arrays.stream(excludeFields).noneMatch(s -> s.equals(field.getName())))
          .collect(Collectors.toList());
    }
    return fieldList;
  }

  /**
   * 类转换，不处理异常
   *
   * @param poData      po数据
   * @param targetClazz voClass
   * @param <T>         po泛型
   * @param <V>         vo泛型
   * @return vo
   */
  public static <T, V> V transferClass(T poData, Class<V> targetClazz, String... excludeFields) {
    try {
      return getClass(poData, targetClazz, excludeFields);
    } catch (IntrospectionException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      log.error("转换class异常： classPo: " + poData.getClass() + "voClass:  " + targetClazz + e.getLocalizedMessage());
      throw new BaseRuntimeException(MessageCode.SERVER_ERROR);
    }
  }

  public static <T, V> void mergeClass(T fromData, V targetData, String... excludeFields) {
    try {
      //获取vo的全部属性值
      List<Field> fields = getClassAllFields(targetData.getClass(), excludeFields);

      //获取po的全部属性名
      List<String> fieldNameList = getClassAllFieldName(fromData.getClass(), excludeFields);
      setFields(fields, fieldNameList, fromData, targetData);
    } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
      log.error(e.getLocalizedMessage());
      throw new BaseRuntimeException(MessageCode.SERVER_ERROR);
    }
  }

  public static <T> T getValue(Object clazz, String fieldName) {
    for (Field field : getClassAllFields(clazz.getClass())) {
      if (fieldName.equals(field.getName())) {
        field.setAccessible(true);
        try {
          return (T) field.get(clazz);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  public static Field getField(Object clazz, String fieldName) {
    for (Field field : getClassAllFields(clazz.getClass())) {
      if (fieldName.equals(field.getName())) {
        return field;
      }
    }
    return null;
  }

  /**
   * 在对象列表中查找重复Field
   *
   * @param list      列表
   * @param fieldName 查询对象中字段名
   * @param <T>       泛型
   * @return 重复元素
   */
  public static <T> List<Object> getDuplicateElements(List<T> list, String fieldName) {
    return list.stream()                              // list 对应的 Stream
        .collect(Collectors.toMap(e -> getValue(e, fieldName), e -> 1, Integer::sum)) // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
        .entrySet().stream()                   // 所有 entry 对应的 Stream
        .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
        .map(Map.Entry::getKey)          // 获得 entry 的键（重复元素）对应的 Stream
        .collect(Collectors.toList());         // 转化为 List
  }

  /**
   * 查找重复元素
   *
   * @param list 列表
   * @param <T>  泛型
   * @return 重复列表
   */
  public static <T> List<T> getDuplicateElements(List<T> list) {
    return list.stream()
        .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
        .entrySet().stream()
        .filter(entry -> entry.getValue() > 1)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }

  /**
   * 取出两个bean相同字段值不相等的字段
   *
   * @param sourceClass 原class
   * @param newClass    新class
   * @param <T>         泛型
   * @return CompareClass
   */
  public static <T> List<CompareClass> findDifferentField(T sourceClass, T newClass, String... excludeFields) {
    List<Field> sourceFieldList = getClassAllFields(sourceClass.getClass(), excludeFields);
    List<Field> newFieldList = getClassAllFields(newClass.getClass(), excludeFields);
    ArrayList<CompareClass> results = new ArrayList<>();
    for (Field sourceField : sourceFieldList) {
      newFieldList.stream().filter(field -> field.getName().equals(sourceField.getName())).findFirst().ifPresent(newField -> {
        try {
          sourceField.setAccessible(true);
          newField.setAccessible(true);
          Object sourceValue = sourceField.get(sourceClass);
          Object newValue = newField.get(newClass);
          if (sourceValue != null && !sourceValue.equals(newValue)) {
            results.add(CompareClass.builder()
                .fieldName(sourceField.getName()).sourceValue(sourceValue).newValue(newValue).fieldType(sourceField.getType())
                .build());
          }
        } catch (Exception e) {
          e.printStackTrace();
          log.error("findDifferentField，" + e.getLocalizedMessage());
          throw new BaseRuntimeException(MessageCode.SERVER_ERROR);
        }
      });
    }
    return results;
  }

  public static <T> List<T> convertEnumToText(List<T> list) {
    return convertEnumToText(list, "Text", "getDescription", "-");
  }

  public static <T> T convertEnumToText(T object) {
    return convertEnumToText(asList(object), "Text", "getDescription", "-").get(0);
  }

  /**
   * 可以把枚举里面的赋值到字符串上
   *
   * @param list                列表
   * @param enumTextLabelSuffix 枚举字符串后缀
   * @param enumTextMethod      枚举赋值字符串的方法
   * @param <T>                 泛型
   * @return 列表
   */
  public static <T> List<T> convertEnumToText(List<T> list, String enumTextLabelSuffix, String enumTextMethod, String defaultValue) {
    return list.stream().peek(t -> {
      List<Field> classAllFields = getClassAllFields(t.getClass()).stream()
          .filter(field -> field.getName().endsWith(enumTextLabelSuffix))
          .collect(Collectors.toList());
      for (Field field : classAllFields) {
        field.setAccessible(true);
        Field enumSourceField = getField(t, field.getName().replace(enumTextLabelSuffix, ""));
        if (enumSourceField != null) {
          enumSourceField.setAccessible(true);
          try {
            Class<?> class_ = Class.forName(enumSourceField.getType().toString().replace("class ", ""));
            if (class_.isEnum()) {
              Optional<?> first = Arrays.stream(class_.getEnumConstants()).filter(o -> {
                try {
                  Object obj = enumSourceField.get(t);
                  return o.equals(obj);
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                }
                return false;
              }).findFirst();
              PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), t.getClass());
              Method writeMethod = propertyDescriptor.getWriteMethod();
              if (first.isPresent()) {
                first.ifPresent(o -> {
                  try {
                    String invokeValue = (String) o.getClass().getMethod(enumTextMethod).invoke(o);
                    writeMethod.invoke(t, invokeValue);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                });
              } else {
                writeMethod.invoke(t, defaultValue);
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          log.warn("后缀为" + field.getName() + "的Field没有找到对应枚举Field，转化失败");
        }
      }
    })
        .collect(Collectors.toList());
  }
}
