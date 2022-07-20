package pers.cc.spring.api.wechat.service;


import pers.cc.spring.api.wechat.model.response.Article;
import pers.cc.spring.api.wechat.model.response.TextMessage;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by iSen on 2016/11/10
 */
public interface WechatResponseService {

    /**
     * 回复文本
     *
     * @param text        文本内容
     * @param requestMap  请求Map
     * @param printWriter 输出Writer
     */
    void responseText(String text, Map<String, String> requestMap, PrintWriter printWriter);

    /**
     * 回复文本
     *
     * @param textMessage 文本Object
     * @param requestMap  请求Map
     * @param printWriter 输出Writer
     */
    void responseText(TextMessage textMessage, Map<String, String> requestMap, PrintWriter printWriter);

    /**
     * 回复图文消息
     *
     * @param articles    图文object
     * @param requestMap  请求Map
     * @param printWriter 输出Writer
     */
    void responseArticles(Map<String, String> requestMap, PrintWriter printWriter, Article... articles);

}
