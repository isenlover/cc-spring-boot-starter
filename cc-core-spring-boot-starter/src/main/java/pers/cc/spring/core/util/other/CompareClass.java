package pers.cc.spring.core.util.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chengce
 * @version 2021-04-01 18:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompareClass {

  private String fieldName;

  private Object sourceValue;

  private Object newValue;

  private Class<?> fieldType;
}
