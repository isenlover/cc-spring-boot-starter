package pers.cc.spring.data.jpa.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chengce
 * @version 2021-02-24 19:35
 */
@Data
@MappedSuperclass
@NoArgsConstructor
@Table(indexes = {
    @Index(columnList = "createTime"),
    @Index(columnList = "updateTime")
})
public class BaseIncrementPO implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(columnDefinition = "int COMMENT'主键'")
  @ApiModelProperty(hidden = true)
  protected String id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(columnDefinition = "datetime(3) COMMENT'创建时间'", updatable = false)
  @ApiModelProperty(hidden = true)
  private Date createTime;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(columnDefinition = "datetime(3) COMMENT'更新时间'")
  @ApiModelProperty(hidden = true)
  private Date updateTime;
}
