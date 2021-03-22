package pers.cc.spring.api.ifeige.model;

import lombok.Data;
import pers.cc.spring.core.util.CommonUtils;

/**
 * @author chengce
 * @version 2020-03-01 16:23
 */
@Data
public class IFeiGeValue {

  private String value;

  private String color;

  public IFeiGeValue() {
  }

  private IFeiGeValue(Builder builder) {
    setValue(builder.value);
    setColor(CommonUtils.isNotEmpty(builder.color) ? builder.color : "#173177");
  }

  public static final class Builder {
    private String value;
    private String color;

    public Builder() {
    }

    public Builder value(String val) {
      value = val;
      return this;
    }

    public Builder color(String val) {
      color = val;
      return this;
    }

    public IFeiGeValue build() {
      return new IFeiGeValue(this);
    }
  }
}
