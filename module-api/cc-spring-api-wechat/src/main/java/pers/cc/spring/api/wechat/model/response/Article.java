package pers.cc.spring.api.wechat.model.response;

import lombok.Data;

@Data
public class Article {
    // 图文消息名称
    private String Title;
    // 图文消息描述  
    private String Description;
    // 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200，限制图片链接的域名需要与开发者填写的基本资料中的Url一致
    private String PicUrl;
    // 点击图文消息跳转链接  
    private String Url;
}
