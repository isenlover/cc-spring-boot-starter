package pers.cc.spring.amqp.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pers.cc.spring.amqp.service.AmqpService;
import pers.cc.spring.core.message.Message;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author chengce
 * @version 2021-03-17 11:49
 */
@Primary
@Service
@ConditionalOnProperty(name = "cc.amqp.single", havingValue = "true")
public class AmqpServiceImpl implements AmqpService {
  @Resource(name = "syncTemplate")
  RabbitTemplate syncRabbitTemplate;

  @Autowired
  RabbitTemplate defaultTemplate;

  private String getContent(Object data) {
    if (data instanceof String) {
      return (String) data;
    } else {
      return JSON.toJSONString(data);
    }
  }

  @Override
  public String sendAndReceive(String routingKey, Object data) {
    Object result = syncRabbitTemplate.convertSendAndReceive(routingKey, getContent(data));
    if (result instanceof byte[]) {
      return new String((byte[]) result, StandardCharsets.UTF_8);
    }
    return (String) result;
  }

  @Override
  public <T> Message<T> sendAndReceive(String routingKey, Object data, Class<T> tClass) {
    String body = sendAndReceive(routingKey, data);
    return Message.fromJson(body, tClass);
  }

  @Override
  public void send(String routingKey, Object data) {
    defaultTemplate.convertAndSend(routingKey, getContent(data));
  }
}
