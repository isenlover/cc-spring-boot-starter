package pers.cc.spring.core.util.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 操作Excel文档的接口
 *
 * @author chengce
 * @version 2016-11-10
 */
// FIXME: 2018/4/30 1->2
public class JdomXmlUtils {

  //    /**
//     * 将map封装成xml
//     *
//     * @param parameters map参数
//     * @return xml
//     */
  public static String getRequestXml(SortedMap<Object, Object> parameters) {
    StringBuilder sb = new StringBuilder();
    sb.append("<xml>");
    Set<Map.Entry<Object, Object>> es = parameters.entrySet();
    for (Object e : es) {
      Map.Entry entry = (Map.Entry) e;
      String k = (String) entry.getKey();
      String v = (String) entry.getValue();
      if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)) {
        sb.append("<").append(k).append(">").append("<![CDATA[").append(v).append("]]></").append(k).append(">");
      } else {
        sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">");
      }
    }
    sb.append("</xml>");
    return sb.toString();
//    return null;
  }

  //
//    /**
//     * 获取子结点的xml
//     *
//     * @param children node
//     * @return String
//     */
  private static String getChildrenText(List children) {
//        StringBuffer sb = new StringBuffer();
//        if (!children.isEmpty()) {
//            for (Object aChildren : children) {
//                Element e = (Element) aChildren;
//                String name = e.getName();
//                String value = e.getTextNormalize();
//                List list = e.getChildren();
//                sb.append("<").append(name).append(">");
//                if (!list.isEmpty()) {
//                    sb.append(getChildrenText(list));
//                }
//                sb.append(value);
//                sb.append("</").append(name).append(">");
//            }
//        }
//
//        return sb.toString();
    return null;
  }

  //
  public static Map<String, Object> doXMLParse(String strxml) throws JDOMException, IOException {
    strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

    if ("".equals(strxml)) {
      return null;
    }

    Map<String, Object> m = new HashMap<>();

    InputStream in = new ByteArrayInputStream(strxml.getBytes(StandardCharsets.UTF_8));
    SAXBuilder builder = new SAXBuilder();
    Document doc = builder.build(in);
    Element root = doc.getRootElement();
    List<Element> list = root.getChildren();
    for (Object aList : list) {
      Element e = (Element) aList;
      String k = e.getName();
      String v;
      List<Element> children = e.getChildren();
      if (children.isEmpty()) {
        v = e.getTextNormalize();
      } else {
        v = getChildrenText(children);
      }

      m.put(k, v);
    }

    //关闭流
    in.close();

    return m;
  }
}
