package pers.cc.spring.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.cc.spring.core.properties.CoreProperties;

/**
 * @author chengce
 * @version 2018-10-10 00:08
 */
@Slf4j
@Service
public class CLog {

  @Autowired
  CoreProperties coreProperties;

  public void info(String info) {
    if (coreProperties.isDebug()) {
      log.info(info);
    }
  }
}
