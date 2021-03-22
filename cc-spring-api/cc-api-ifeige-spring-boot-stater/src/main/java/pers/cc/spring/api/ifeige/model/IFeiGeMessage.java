package pers.cc.spring.api.ifeige.model;

import lombok.Data;

/**
 * @author chengce
 * @version 2020-03-01 16:22
 */
@Data
public class IFeiGeMessage {

  private String secret;

  private String app_key;

  private String template_id;

  private String url;

  private IFeiGeData data;

  public IFeiGeMessage() {
  }

  private IFeiGeMessage(Builder builder) {
    setSecret(builder.secret);
    setApp_key(builder.app_key);
    setTemplate_id(builder.template_id);
    setUrl(builder.url);
    setData(builder.data);
  }

  public static final class Builder {
    private String secret;
    private String app_key;
    private String template_id;
    private String url;
    private IFeiGeData data;

    public Builder() {
    }

    public Builder secret(String val) {
      secret = val;
      return this;
    }

    public Builder app_key(String val) {
      app_key = val;
      return this;
    }

    public Builder template_id(String val) {
      template_id = val;
      return this;
    }

    public Builder url(String val) {
      url = val;
      return this;
    }

    public Builder data(IFeiGeData val) {
      data = val;
      return this;
    }

    public IFeiGeMessage build() {
      return new IFeiGeMessage(this);
    }
  }
}
