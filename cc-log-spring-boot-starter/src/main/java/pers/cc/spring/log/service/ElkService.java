package pers.cc.spring.log.service;

/**
 * @author chengce
 * @version 2019-02-19 09:38
 */
public interface ElkService {

  void log(String description, String operation);

  void log(String description);

  void error(String description);

  void error(String description, String operation);
}
