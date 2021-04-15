package pers.cc.spring.core.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import pers.cc.spring.core.util.CommonUtils;

/**
 * 消息类
 *
 * @author chengce
 * @version 2017-10-10 18:39
 * @fixed 20210-2
 */
@ApiModel("消息基类，所有返回值(包含异常)均在此类中")
@Data
public class Message<T> {
  /**
   * 消息编号
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ApiModelProperty("自定义状态码，也包含第三方返回码")
  private String code;
  /**
   * 消息
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ApiModelProperty("自定义消息，可用于前端显示")
  private String message;
  /**
   * 详细的不合法字段提示
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @ApiModelProperty("详细的不合法字段提示")
  private String errorFields;
  /**
   * 一般为成功返回的数据
   */
  @ApiModelProperty("接口数据")
  private T data;
  /**
   * 是否成功
   */
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @ApiModelProperty(value = "接口调用排除异常外的结果，前端一般不用判断，一般也不会序列化到前端", example = "false")
  private boolean success;

  public Message() {
    success = false;
    data = null;
  }

  public Message(T data) {
    this.data = data;
  }

  public Message<T> fromJson(String json) {
    if (json == null) {
      return Message.<T>failed().message("json is null").build();
    }
    try {
      return JSON.parseObject(json, new TypeReference<Message<T>>() {
      }.getType());
    } catch (Exception e) {
      return Message.<T>failed().message("json is null and " + e.getLocalizedMessage()).build();
    }
  }

//    public <T> Message<T> fromJsonList(String json) {
//        if (json == null) {
//            return Message.failed().message("json is null").build();
//        }
//        try {
//            return JSON.parseArray(json, new TypeReference<Message<T>>() {}.getType());
//        } catch (Exception e) {
//            return Message.failed().message("json is null and " + e.getLocalizedMessage()).build();
//        }
//    }

  private Message(Builder<T> builder,
                  T data) {
    setCode(builder.code);
    setMessage(builder.message);
    setData(data);
    setSuccess(builder.success);
    setErrorFields(builder.errorFields);
  }

  public static <T> Message<T> ok(T data) {
    Builder<T> builder = builder();
    builder.success(true);
    builder.data(data);
    return builder.build();
  }

  public static <T> ResponseEntity<Message<T>> responseOk(T data) {
    Builder<T> builder = builder();
    builder.success(true);
    builder.data(data);
    return ResponseEntity.ok(builder.build());
  }

  public static <T> ResponseEntity<Message<T>> responseOk() {
    Builder<T> builder = builder();
    builder.success(true);
    return ResponseEntity.ok(builder.build());
  }

  public static <T> Message.Builder<T> ok() {
    Builder<T> builder = builder();
    builder.success(true);
    return builder;
  }

  public static <T> Message.Builder<T> failed() {
    Builder<T> builder = builder();
    builder.success(false);
    builder.data(null);
    return builder;
  }

  public static <T> Message.Builder<T> builder() {
    return new Builder<>();
  }

  public static <T> Message<T> fromJson(String json, Class<T> tClass) {
    if (json == null) {
      return Message.<T>failed().message("json is null").build();
    }
    try {
      return JSON.parseObject(json, new TypeReference<Message<T>>(tClass) {
      }.getType());
    } catch (Exception e) {
      return Message.<T>failed().message("json is null and " + e.getLocalizedMessage()).build();
    }
  }

  public static <T> Message<T> fromJson(String json, Object tClass) {
    if (json == null) {
      return Message.<T>failed().message("json is null").build();
    }
    try {
      return JSON.parseObject(json, new TypeReference<Message<T>>(tClass.getClass()) {
      }.getType());
    } catch (Exception e) {
      return Message.<T>failed().message("json is null and " + e.getLocalizedMessage()).build();
    }
  }

  public void ifPresent(MessageFunction<T> messageFunction) {
    if (this.isSuccess() && CommonUtils.isNotEmpty(data)) {
      messageFunction.callback(data);
    }
  }

  public static final class Builder<T> {
    private String code;
    private String message;
    private String errorFields;
    private T data;
    private boolean success;

    private Builder() {
    }

    public Builder<T> code(String val) {
      code = val;
      return this;
    }

    public Builder<T> message(String val) {
      message = val;
      return this;
    }

    public Builder<T> errorFields(String val) {
      errorFields = val;
      return this;
    }

    public Builder<T> data(T val) {
      data = val;
      return this;
    }

    public Builder<T> messageCode(MessageCode messageCode) {
      code = messageCode.getCode();
      message = messageCode.getMessage();
      return this;
    }

    public Builder<T> messageCode(WechatMessageCode messageCode) {
      code = messageCode.getCode();
      message = messageCode.getMessage();
      return this;
    }

    public Builder<T> messageCode(SmsMessageCode messageCode) {
      code = messageCode.getCode();
      message = messageCode.getMessage();
      return this;
    }

    public Builder<T> messageCode(OssMessageCode messageCode) {
      code = messageCode.getCode();
      message = messageCode.getMessage();
      return this;
    }

    public Builder<T> messageCode(NimMessageCode messageCode) {
      code = messageCode.getCode();
      message = messageCode.getMessage();
      return this;
    }

    private Builder<T> success(boolean val) {
      success = val;
      return this;
    }

    public Message<T> build() {
      return new Message<>(this, this.data);
    }
  }

  @FunctionalInterface
  public interface MessageFunction<T> {
    void callback(T t);
  }
}
