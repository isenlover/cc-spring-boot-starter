package pers.cc.spring.data.jpa.strategy;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;
import pers.cc.spring.core.util.Snowflake;
import pers.cc.spring.core.util.SpringContextUtils;

import java.io.Serializable;

/**
 * @author chengce
 * @version 2021-03-25 12:22
 */
@Slf4j
@Component
public class GenerateSnowflakeIdStrategy implements IdentifierGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
    Snowflake snowflake = SpringContextUtils.getBean(Snowflake.class);
    return snowflake.nextId();
  }
}
