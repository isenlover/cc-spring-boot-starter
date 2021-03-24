package pers.cc.spring.api.core.model;

import lombok.extern.slf4j.Slf4j;
import pers.cc.spring.api.core.service.CrawlerApiService;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.other.ClassUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 爬虫城市列表+工具类
 *
 * @author chengce
 * @version 2018-01-18 13:44
 * @see CrawlerApiService#getCities()
 */
@Slf4j
public class Cities extends ArrayList<City> implements List<City> {

  /**
   * 获取省
   *
   * @param code 市代码
   * @return 省
   */
  public City getProvince(String code) {
    Optional<City> cityOptional = super.stream().filter(
        city -> city.getCode().substring(0, 2).equals(code.substring(0, 2))).findFirst();
    return cityOptional.orElse(null);
  }

  /**
   * 获取市
   *
   * @param code 区县代码
   * @return 市
   */
  public City getCity(String code) {
    City province = this.getProvince(code);
    if (CommonUtils.isEmpty(province.getCities())) {
      return null;
    }
    Optional<City> cityOptional = province.getCities().stream().filter(
        city -> city.getCode().substring(0, 4).equals(code.substring(0, 4))).findFirst();
    return cityOptional.orElse(null);
  }

  /**
   * 省是否已存储市
   *
   * @param code 市或区县代码
   * @return 是否包含
   */
  public boolean containsCity(String code) {
    City province = this.getProvince(code);
    return !CommonUtils.isEmpty(province.getCities()) && province.getCities().stream().anyMatch(
        city -> city.getCode().substring(0, 4).equals(code.substring(0, 4)));
  }

  private <T> List<T> getList(List<City> cities,
                              Class<T> tClass) {
    List<T> ret = new ArrayList<>();
    cities.forEach(city -> {
      try {
        T data;
        if (tClass != null) {
          data = ClassUtils.getClass(city, tClass);
          ret.add(data);
        } else {
          log.error("转换类型为空");
        }
      } catch (IntrospectionException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
        log.error("城市转换出错");
      }
      if (CommonUtils.isNotEmpty(city.getCities())) {
        ret.addAll(getList(city.getCities(), tClass));
      }
    });
    return ret;
  }

  private List<City> getList(List<City> cities) {
    List<City> ret = new ArrayList<>();
    cities.forEach(city -> {
      ret.add(city);
      if (CommonUtils.isNotEmpty(city.getCities())) {
        ret.addAll(getList(city.getCities()));
      }
      city.setCities(null);
    });
    return ret;
  }

  /**
   * 获取整个城市列表
   *
   * @return 列表
   */
  public List<City> getList() {
    return getList(this);
  }

  /**
   * 获取整个城市列表
   *
   * @param tClass 需要转换的类
   * @param <T>    泛型
   * @return 列表
   */
  public <T> List<T> getList(Class<T> tClass) {
    return getList(this, tClass);
  }
}
