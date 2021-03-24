package pers.cc.spring.log.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.cc.spring.log.elk.enums.ELKLogType;
import pers.cc.spring.log.elk.model.Log;

/**
 * @author chengce
 * @version 2019-02-19 09:38
 */
@Slf4j
@Service
public class ElkServiceImpl implements ElkService {

  @Override
  public void log(String description, String operation) {
    log.info(JSON.toJSONString(new Log.Builder().description(description).operation(operation).build()));
  }

  @Override
  public void log(String description) {
    log.info(JSON.toJSONString(new Log.Builder().description(description).operation("CONSOLE").build()));
  }

  @Override
  public void error(String description) {
    log.error(JSON.toJSONString(new Log.Builder().description(description).type(ELKLogType.EXCEPTION).operation("CONSOLE").build()));
  }

  @Override
  public void error(String description, String operation) {
    log.error(JSON.toJSONString(new Log.Builder().description(description).type(ELKLogType.EXCEPTION).operation(operation).build()));
  }
}
