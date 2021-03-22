package pers.cc.spring.api.ifeige.model;

import lombok.Data;

/**
 * @author chengce
 * @version 2020-03-01 16:22
 */
@Data
public class IFeiGeData {

  private IFeiGeValue first;

  private IFeiGeValue content;

  private IFeiGeValue occurtime;

  private IFeiGeValue remark;

  public IFeiGeData() {
  }

  private IFeiGeData(Builder builder) {
    setFirst(builder.first);
    setContent(builder.content);
    setOccurtime(builder.occurtime);
    setRemark(builder.remark);
  }

  public static final class Builder {
    private IFeiGeValue first;
    private IFeiGeValue content;
    private IFeiGeValue occurtime;
    private IFeiGeValue remark;

    public Builder() {
    }

    public Builder first(IFeiGeValue val) {
      first = val;
      return this;
    }

    public Builder content(IFeiGeValue val) {
      content = val;
      return this;
    }

    public Builder occurtime(IFeiGeValue val) {
      occurtime = val;
      return this;
    }

    public Builder remark(IFeiGeValue val) {
      remark = val;
      return this;
    }

    public IFeiGeData build() {
      return new IFeiGeData(this);
    }
  }
}
