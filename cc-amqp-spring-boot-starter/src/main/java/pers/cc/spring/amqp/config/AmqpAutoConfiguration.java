package pers.cc.spring.amqp.config;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * @author chengce
 * @version 2018-07-12 11:56
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@Import(AmqpConfiguration.class)
public class AmqpAutoConfiguration {
}
