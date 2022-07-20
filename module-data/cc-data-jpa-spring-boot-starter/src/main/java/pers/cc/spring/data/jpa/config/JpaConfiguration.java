package pers.cc.spring.data.jpa.config;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pers.cc.spring.core.util.Snowflake;
import pers.cc.spring.data.jpa.property.JpaProperties;
import pers.cc.spring.data.jpa.strategy.JpaTableNamingStrategy;


/**
 * naming @link https://www.baeldung.com/hibernate-field-naming-spring-boot
 *
 * @author chengce
 * @version 2021-03-24 12:35
 */
@ComponentScan("pers.cc.spring.data.jpa")
@EnableConfigurationProperties(JpaProperties.class)
public class JpaConfiguration {

  @Autowired
  JpaProperties jpaProperties;

  @Bean
  public PhysicalNamingStrategy physical() {
    return new JpaTableNamingStrategy();
  }

  @Bean
  public ImplicitNamingStrategy implicit() {
    return new ImplicitNamingStrategyLegacyJpaImpl();
  }

  @Bean
  Snowflake snowflake() {
    return new Snowflake(jpaProperties.getSnowflakeWorkId(), jpaProperties.getSnowflakeMachineId());
  }
}
