package pers.cc.spring.data.jpa.config;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import pers.cc.spring.data.jpa.strategy.JpaTableNamingStrategy;


/**
 * naming @link https://www.baeldung.com/hibernate-field-naming-spring-boot
 * @author chengce
 * @version 2021-03-24 12:35
 */
@ComponentScan("pers.cc.spring.data.jpa")
public class JpaConfiguration {

  @Bean
  public PhysicalNamingStrategy physical() {
    return new JpaTableNamingStrategy();
  }

  @Bean
  public ImplicitNamingStrategy implicit() {
    return new ImplicitNamingStrategyLegacyJpaImpl();
  }

}
