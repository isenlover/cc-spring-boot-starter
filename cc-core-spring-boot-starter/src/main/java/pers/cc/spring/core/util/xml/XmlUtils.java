package pers.cc.spring.core.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作Excel文档的接口
 *
 * @author chengce
 * @version 2016-11-10
 */
public class XmlUtils {

    /**
     * 扩展xstream，使其支持CDATA块
     */
    protected static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * dom4j 解析xml
     *
     * @param inputStream 输入流
     * @return map
     * @throws DocumentException 异常
     * @throws IOException       异常
     */
    public static Map<String, String> analyzeXmlByDom4j(InputStream inputStream) throws DocumentException, IOException {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();

        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        return map;
    }

    /**
     * object转成xml
     *
     * @param object object
     * @return xml
     */
    public static String toXml(Object object) {
        xstream.alias("xml", object.getClass());
        return xstream.toXML(object);
    }
}
