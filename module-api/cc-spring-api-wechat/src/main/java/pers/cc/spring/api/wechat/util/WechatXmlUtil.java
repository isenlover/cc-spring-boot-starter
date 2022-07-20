package pers.cc.spring.api.wechat.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pers.cc.spring.api.wechat.model.response.Article;
import pers.cc.spring.api.wechat.model.response.ArticleMessage;
import pers.cc.spring.core.util.xml.XmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iSen on 2016/11/10
 */
public class WechatXmlUtil extends XmlUtils {

  public static String toXml(ArticleMessage articleMessage) {
    xstream.alias("xml", articleMessage.getClass());
    xstream.alias("item", Article.class);
    return xstream.toXML(articleMessage);
  }

  /**
   * 接收微信端的内容将xml转为map
   *
   * @param request request
   * @return map
   */
  public static Map<String, String> xmlToMap(HttpServletRequest request) {
    Map<String, String> map = new HashMap<>();
    SAXReader reader = new SAXReader();
    InputStream ins = null;
    try {
      ins = request.getInputStream();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    Document doc = null;
    try {
      doc = reader.read(ins);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    Element root = null;
    if (doc != null) {
      root = doc.getRootElement();
    }
    List<Element> list = root.elements();
    for (Element e : list) {
      map.put(e.getName(), e.getText());
    }
    try {
      ins.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return map;
  }


}
