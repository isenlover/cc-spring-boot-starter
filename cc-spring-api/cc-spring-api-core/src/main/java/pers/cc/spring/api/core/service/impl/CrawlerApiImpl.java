package pers.cc.spring.api.core.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.core.model.Cities;
import pers.cc.spring.api.core.model.City;
import pers.cc.spring.api.core.service.CrawlerApiService;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.http.HttpUtils;
import pers.cc.spring.core.util.other.RegExpUtils;

import java.util.regex.Matcher;

/**
 * @author chengce
 * @version 2018-01-18 12:59
 */
@Service
@Slf4j
@Deprecated
public class CrawlerApiImpl implements CrawlerApiService {

  @Override
  public Cities getCities() {
    Cities cities = new Cities();
    String resp;
    try {
      resp = HttpUtils.httpGet("http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201703/t20170310_1471429.html");
      // 匹配省市区列表字符串
      Matcher matchList = RegExpUtils.builder(
          "(?<=<div class=\"TRS_PreAppend\" style=\"overflow-x: hidden; word-break: break-all\">)(.+?)(?=</div>)").find(resp.replace("&nbsp;", ""));
      while (matchList.find()) {
        String content = matchList.group();
        // 匹配省市区详细
        Matcher matchInfo = RegExpUtils.builder("(?<=<p class=\"MsoNormal\">)(.+?)(?=</p>)").find(content);
        City item;
        while (matchInfo.find()) {
          item = new City();
          String info = matchInfo.group();
          Matcher matchCode = RegExpUtils.builder("(?<=<span lang=\"EN-US\">)(.+?)(?=<span>)").find(info);
          Matcher matchName = RegExpUtils.builder("(?<=<span style=\"font-family: 宋体\">)(.+?)(?=</span>)").find(info);
          String code = matchCode.find() ? matchCode.group() : "";
          String name = null;
          while (matchName.find()) {
            name = matchName.group().replace("　", "");
            if (CommonUtils.isNotEmpty(name)) {
              break;
            }
          }
          item.setCode(code);
          item.setName(name);
          if (info.contains("<b>")) {
            // 省
            cities.add(item);
          } else {
            // 市
            if (!cities.containsCity(code)) {
              City province = cities.getProvince(code);
              if (province == null) {
                log.error("没有查询到对应省，code： " + code);
                continue;
              }
              item.setPid(province.getCode());
              province.getCities().add(item);
            } else {
              // 区县
              City city = cities.getCity(code);
              if (city == null) {
                log.error("没有查询到对应市，code： " + code);
                continue;
              }
              item.setPid(city.getCode());
              city.getCities().add(item);
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("爬虫城市列表失败");
    }

    return cities;
  }
}
