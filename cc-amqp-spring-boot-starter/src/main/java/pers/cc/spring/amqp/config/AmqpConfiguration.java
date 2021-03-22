package pers.cc.spring.amqp.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pers.cc.spring.amqp.properties.AmqpProperties;

/**
 * @author chengce
 * @version 2021-02-09 14:42
 */
@Configuration
@ConditionalOnProperty(name = "cc.amqp.single", havingValue = "true")
public class AmqpConfiguration {
  @Autowired
  AmqpProperties amqpProperties;

  @Primary
  @Bean(name = "myConnectionFactory")
  public ConnectionFactory myConnectionFactory() {
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost(amqpProperties.getHost());
    connectionFactory.setPort(amqpProperties.getPort());
//        connectionFactory.setRequestedHeartBeat(3600);
    connectionFactory.setConnectionTimeout(0);
    connectionFactory.setUsername(amqpProperties.getUsername());
    connectionFactory.setPassword(amqpProperties.getPassword());
//        connectionFactory.setPublisherConfirms(true);
    return connectionFactory;
  }

  @Primary
  @Bean(name = "defaultTemplate")
  public RabbitTemplate defaultTemplate(@Qualifier("myConnectionFactory") ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory);
  }

  @Bean(name = "syncTemplate")
  public RabbitTemplate syncRabbitTemplate(@Qualifier("myConnectionFactory") ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setUseDirectReplyToContainer(false);
    rabbitTemplate.setReplyTimeout(amqpProperties.getReplayTime());
    return rabbitTemplate;
  }

  @Bean(name = "myFactory")
  public SimpleRabbitListenerContainerFactory myFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                        @Qualifier("myConnectionFactory") ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    configurer.configure(factory, connectionFactory);
    return factory;
  }
}
