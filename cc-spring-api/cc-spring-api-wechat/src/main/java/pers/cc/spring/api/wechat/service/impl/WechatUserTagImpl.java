package pers.cc.spring.api.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.wechat.annotation.EnableWechatOfficialAccount;
import pers.cc.spring.api.wechat.enums.url.WechatTagUrl;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;
import pers.cc.spring.api.wechat.model.user.*;
import pers.cc.spring.api.wechat.service.WechatUserTagService;
import pers.cc.spring.api.wechat.util.WechatUtil;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.http.HttpUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CC on 2016-11-17 16:55
 */
@Service
@ConditionalOnBean(annotation = EnableWechatOfficialAccount.class)
public class WechatUserTagImpl extends WechatUserTagAbstract implements WechatUserTagService {

  public Message<TagMessage> createTag(String tagName) {
    Message<TagMessage> httpMessage = new Message<>();
    TagMessage tagMessage = new TagMessage();
    String requestUrl = WechatUtil.getRealUrlReplaceAccessToken(WechatTagUrl.createUrl.getUrl());

    tagMessage.setTag(new Tag(tagName));
    try {
      String response = HttpUtils.httpsPost(requestUrl, JSON.toJSONString(tagMessage));
      tagMessage = JSON.parseObject(response, TagMessage.class);
      if (WechatUtil.checkRequest(httpMessage, tagMessage)) {
        httpMessage.setData(tagMessage);
      }
    } catch (Exception e) {
      return httpMessage;
    }
    return httpMessage;
  }

  public Message<List<Tag>> getTags() {
    Message<List<Tag>> httpMessage = new Message<>();
    TagListMessage tagListMessage;
    String requestUrl = WechatUtil.getRealUrlReplaceAccessToken(WechatTagUrl.getUrl.getUrl());

    try {
      String response = HttpUtils.httpsGet(requestUrl);
      tagListMessage = JSON.parseObject(response, TagListMessage.class);
      if (WechatUtil.checkRequest(httpMessage, tagListMessage)) {
        List<Tag> tagList = Arrays.asList(tagListMessage.getTags());
        httpMessage.setData(tagList);
      }
    } catch (Exception e) {
      return httpMessage;
    }
    return httpMessage;
  }

  public Message<Integer> editTag(int tagID, String tagName) {
    Tag tag = new Tag(tagID, tagName);
    return editTag(tag);
  }

  public Message<Integer> editTag(Tag tag) {
    Message<Integer> httpMessage = new Message<>();
    httpMessage.setData(tag.getId());
    String requestUrl = WechatUtil.getRealUrlReplaceAccessToken(WechatTagUrl.editUrl.getUrl());

    TagMessage tagMessage = new TagMessage();
    tagMessage.setTag(tag);
    try {
      String response = HttpUtils.httpsPost(requestUrl, JSON.toJSONString(tagMessage));
      WxBaseResponse baseErr = JSON.parseObject(response, WxBaseResponse.class);
      WechatUtil.checkRequest(httpMessage, baseErr);
    } catch (Exception e) {
      return httpMessage;
    }
    return httpMessage;
  }

  public List<Message<Integer>> editTag(List<Tag> tagList) {
    List<Message<Integer>> httpMessage = new ArrayList<>();
    for (Tag tag : tagList) {
      httpMessage.add(editTag(tag));
    }
    return httpMessage;
  }

  public Message<Integer> deleteTag(int tagID) {
    Message<Integer> httpMessage = new Message<>();
    TagMessage tagMessage = new TagMessage();
    String requestUrl = WechatUtil.getRealUrlReplaceAccessToken(WechatTagUrl.deleteUrl.getUrl());

    tagMessage.setTag(new Tag(tagID));
    try {
      WxBaseResponse baseErr;
      String response = HttpUtils.httpsPost(requestUrl, JSON.toJSONString(tagMessage));
      baseErr = JSON.parseObject(response, WxBaseResponse.class);
      if (WechatUtil.checkRequest(httpMessage, baseErr)) {
        httpMessage.setData(tagID);
      }
    } catch (Exception e) {
      return httpMessage;
    }
    return httpMessage;
  }

  public List<Message<Integer>> deleteTag(List<Integer> tagList) {
    List<Message<Integer>> httpMessage = new ArrayList<>();
    for (int tagID : tagList) {
      httpMessage.add(deleteTag(tagID));
    }
    return httpMessage;
  }

  private UserTagResponseMessage getUsersInTag(int tagID, String next_openid) {
    UserTagResponseMessage userTagResponseMessage;
    UserTagRequest userTagRequest = new UserTagRequest(tagID);
    if (CommonUtils.isNotEmpty(next_openid)) {
      userTagRequest.setNext_openid(next_openid);
    }
    String requestUrl = WechatUtil.getRealUrlReplaceAccessToken(WechatTagUrl.getFansListTagUrl.getUrl());
    try {
      String response = HttpUtils.httpsPost(requestUrl, JSON.toJSONString(userTagRequest));
      userTagResponseMessage = JSON.parseObject(response, UserTagResponseMessage.class);
    } catch (Exception e) {
      userTagResponseMessage = new UserTagResponseMessage();
      // FIXME: 2018/6/1
//            userTagResponseMessage.setErrcode(Integer.valueOf(CcHttpCode.WECHATERROR.toString()));
//            userTagResponseMessage.setErrmsg(CcHttpCode.WECHATERROR.getMessage());
      return userTagResponseMessage;
    }
    return userTagResponseMessage;
  }

  public Message<Integer> getUserCount(int tagID) {
    return getUserCount(tagID, "");
  }

  public Message<Integer> getUserCount(int tagID, String next_openid) {
    Message<Integer> httpMessage = new Message<>();
    try {
      UserTagResponseMessage userTagResponseMessage = getUsersInTag(tagID, next_openid);
      if (WechatUtil.checkRequest(httpMessage, userTagResponseMessage)) {
        httpMessage.setData(userTagResponseMessage.getCount());
      }
    } catch (Exception e) {
      return httpMessage;
    }
    return httpMessage;
  }

  public Message<List<UserMessage>> getUserList(int tagID) {
    return getUserList(tagID, "");
  }

  public Message<List<UserMessage>> getUserList(int tagID, String next_openid) {
    Message<List<UserMessage>> httpMessage = new Message<>();
    try {
      List<String> openIdList;
      List<UserMessage> userMessageList = new ArrayList<>();
      UserTagResponseMessage userTagResponseMessage = getUsersInTag(tagID, next_openid);
      if (WechatUtil.checkRequest(httpMessage, userTagResponseMessage)) {
        openIdList = Arrays.asList(userTagResponseMessage.getData().getOpenid());
        for (String openID : openIdList) {
          Message<UserMessage> userhttpMessage = getUser(openID);
          if (userhttpMessage.isSuccess()) {
            userMessageList.add(userhttpMessage.getData());
          }
        }

        httpMessage.setData(userMessageList);
      }
    } catch (Exception e) {
      return httpMessage;
    }
    return httpMessage;
  }

  public Message<List<String>> getUserOpenIDList(int tagID) {
    return getUserOpenIDList(tagID, "");
  }

  public Message<List<String>> getUserOpenIDList(int tagID, String next_openid) {
    Message<List<String>> httpMessage = new Message<>();
    try {
      List<String> openIdList;
      UserTagResponseMessage userTagResponseMessage = getUsersInTag(tagID, next_openid);
      if (WechatUtil.checkRequest(httpMessage, userTagResponseMessage)) {
        openIdList = Arrays.asList(userTagResponseMessage.getData().getOpenid());
        httpMessage.setData(openIdList);
      }
    } catch (Exception e) {
      return httpMessage;
    }
    return httpMessage;
  }
}
