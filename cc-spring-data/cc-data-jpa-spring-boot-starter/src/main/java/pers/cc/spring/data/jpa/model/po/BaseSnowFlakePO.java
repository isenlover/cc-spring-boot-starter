package pers.cc.spring.data.jpa.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 主键为雪花id
 *
 * @author chengce
 * @version 2021-03-25 12:40
 */
@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@Table(indexes = {
    @Index(columnList = "createTime"),
    @Index(columnList = "updateTime")
})
public class BaseSnowFlakePO implements Serializable {
  @Id
  @GenericGenerator(name = "snowflakeId", strategy = "pers.cc.spring.data.jpa.strategy.GenerateSnowflakeIdStrategy")
  @GeneratedValue(generator = "snowflakeId")
  @Column(columnDefinition = "bigint(20) COMMENT'主键'")
  @ApiModelProperty(hidden = true)
  private long id;

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
}
