package pers.cc.spring.amqp.service;

import pers.cc.spring.core.message.Message;

/**
 * @author chengce
 * @version 2021-03-17 11:48
 */
public interface AmqpService {

  String sendAndReceive(String routingKey, Object data);

  <T> Message<T> sendAndReceive(String routingKey, Object data, Class<T> tClass);

  void send(String routingKey, Object data);
}
