package pers.cc.spring.api.ifeige.service;

import pers.cc.spring.api.ifeige.model.IFeiGeData;
import pers.cc.spring.api.ifeige.model.IFeiGeMessage;
import pers.cc.spring.api.ifeige.model.IFeiGeResponse;
import pers.cc.spring.core.message.Message;

/**
 * @author chengce
 * @version 2020-03-01 16:18
 */
public interface IFeiGeService {

  Message<IFeiGeResponse> sendMessage(IFeiGeMessage message) throws Exception;

  Message<IFeiGeResponse> sendMessage(IFeiGeData data) throws Exception;

}
