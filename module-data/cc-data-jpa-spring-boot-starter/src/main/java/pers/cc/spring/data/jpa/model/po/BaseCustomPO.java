package pers.cc.spring.data.jpa.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * jpa基础类
 * 主键为代码控制
 *
 * @author chengce
 * @version 2017-10-05 16:40
 */
@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@Table(indexes = {
    @Index(columnList = "createTime"),
    @Index(columnList = "updateTime")
})
public class BaseCustomPO implements Serializable {
  @Id
  @Column(columnDefinition = "varchar(100) COMMENT'主键'")
  @ApiModelProperty(hidden = true)
  private String id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(columnDefinition = "datetime COMMENT'创建时间'", updatable = false)
  @ApiModelProperty(hidden = true)
  private Date createTime;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(columnDefinition = "datetime COMMENT'更新时间'")
  @ApiModelProperty(hidden = true)
  private Date updateTime;

  public BaseCustomPO(String id) {
    this.id = id;
  }
}
