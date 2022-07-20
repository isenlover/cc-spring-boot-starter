package pers.cc.spring.data.jpa;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pers.cc.spring.data.jpa.config.JpaImport;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@EnableJpaRepositories
@EnableAspectJAutoProxy(exposeProxy = true)
@Import(JpaImport.class)
public class JpaAutoConfiguration {

}