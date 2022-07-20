package pers.cc.spring.api.core.model;

import lombok.Data;

/**
 * 城市类，爬虫 http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201703/t20170310_1471429.html
 *
 * @author chengce
 * @version 2018-01-18 12:54
 */
@Data
public class City {
  /**
   * 城市名字
   */
  private String name;
  /**
   * 城市代码
   */
  private String code;
  /**
   * 父类城市代码
   */
  private String pid;
  /**
   * 子城市
   */
  private Cities cities = new Cities();

}
