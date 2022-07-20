package pers.cc.spring.api.core.service;


import pers.cc.spring.api.core.model.Cities;

/**
 * 爬虫工具
 *
 * @author chengce
 * @version 2018-01-18 12:58
 */
@Deprecated
public interface CrawlerApiService {
  /**
   * 获取中国所有省市区
   *
   * @return 省市区列表
   */
  Cities getCities();
}
