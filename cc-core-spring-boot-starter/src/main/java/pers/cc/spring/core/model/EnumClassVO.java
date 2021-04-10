package pers.cc.spring.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chengce
 * @version 2021-03-02 17:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("所有KeyValue模型")
public class EnumClassVO<T> {

  @ApiModelProperty(value = "标签，不会为空", required = true)
  private String label;

  @ApiModelProperty("值，一般用于传参到后台，可能为空，空时不会返回")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String value;

  @ApiModelProperty("额外的数据，不存在时不会返回")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  @ApiModelProperty("子类，空时不会返回")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private List<EnumClassVO<T>> children;
}
