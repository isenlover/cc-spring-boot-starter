package pers.cc.spring.api.wechat.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.model.response.Article;
import pers.cc.spring.api.wechat.model.response.ArticleMessage;
import pers.cc.spring.api.wechat.model.response.BaseMessage;
import pers.cc.spring.api.wechat.model.response.TextMessage;
import pers.cc.spring.api.wechat.service.WechatResponseService;
import pers.cc.spring.api.wechat.util.WechatXmlUtil;
import pers.cc.spring.core.util.xml.XmlUtils;

import java.io.PrintWriter;
import java.util.*;

/**
 * Created by iSen on 2016/11/10
 */
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatResponseImpl implements WechatResponseService {

  private void initBaseMessage(BaseMessage baseMessage, Map<String, String> requestMap) {
    // 发送方帐号（open_id）
    String fromUserName = requestMap.get("FromUserName");
    // 公众帐号
    String toUserName = requestMap.get("ToUserName");
    // 消息创建时间
    String createTime = requestMap.get("CreateTime");

    baseMessage.setFromUserName(toUserName);
    baseMessage.setToUserName(fromUserName);
    baseMessage.setCreateTime(new Date().getTime());
  }

  public void responseText(String text, Map<String, String> requestMap, PrintWriter printWriter) {
    TextMessage textMessage = new TextMessage();

    textMessage.setFuncFlag(0);
    textMessage.setContent(text);
    responseText(textMessage, requestMap, printWriter);
  }

  public void responseText(TextMessage textMessage, Map<String, String> requestMap, PrintWriter printWriter) {
    initBaseMessage(textMessage, requestMap);

    printWriter.print(XmlUtils.toXml(textMessage));
    printWriter.close();
  }

  public void responseArticles(Map<String, String> requestMap, PrintWriter printWriter, Article... articles) {
    if (articles != null) {
      ArticleMessage articleMessage = new ArticleMessage();
      initBaseMessage(articleMessage, requestMap);

      articleMessage.setArticleCount(articles.length);
      List<Article> articleList = new ArrayList<Article>();
      Collections.addAll(articleList, articles);
      articleMessage.setArticles(articleList);

      printWriter.print(WechatXmlUtil.toXml(articleMessage));
      printWriter.close();
    }

  }
}
