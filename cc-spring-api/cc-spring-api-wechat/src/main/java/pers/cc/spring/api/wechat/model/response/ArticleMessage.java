package pers.cc.spring.api.wechat.model.response;


import lombok.Data;
import pers.cc.spring.api.wechat.enums.WechatMessageType;

import java.util.List;

@Data
public class ArticleMessage extends BaseMessage {
	// 图文消息个数，限制为10条以内  
    private int ArticleCount;  
    // 多条图文消息信息，默认第一个item为大图  
    private List<Article> Articles;

    public ArticleMessage() {
        this.setMsgType(WechatMessageType.response_news.getType());
    }
}
