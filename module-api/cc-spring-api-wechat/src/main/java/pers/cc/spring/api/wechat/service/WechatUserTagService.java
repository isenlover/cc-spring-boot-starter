package pers.cc.spring.api.wechat.service;


import pers.cc.spring.api.wechat.model.user.Tag;
import pers.cc.spring.api.wechat.model.user.TagMessage;
import pers.cc.spring.api.wechat.model.user.UserMessage;
import pers.cc.spring.core.message.Message;

import java.util.List;

/**
 * Created by CC on 2016-11-17 16:40
 */
public interface WechatUserTagService {

    /**
     * 创建用户标签
     *
     * @param tagName 标签名
     * @return UserTag
     */
    Message<TagMessage> createTag(String tagName);

    /**
     * 获取所有标签
     *
     * @return List<Tag>
     */
    Message<List<Tag>> getTags();

    /**
     * 编辑某个标签
     *
     * @param tagID   标签ID
     * @param tagName 标签名
     * @return Message<String>
     */
    Message<Integer> editTag(int tagID, String tagName);

    /**
     * 编辑某个标签
     *
     * @param tag 标签实例
     * @return Message<String>
     */
    Message<Integer> editTag(Tag tag);

    /**
     * 编辑多个标签
     *
     * @param tagList 标签ID
     * @return List<Message<Integer>>
     */
    List<Message<Integer>> editTag(List<Tag> tagList);

    /**
     * 删除某个标签
     *
     * @param tagID 标签ID
     * @return Message<String>
     */
    Message<Integer> deleteTag(int tagID);

    /**
     * 批量删除标签
     *
     * @param tagList 标签列表ID
     * @return Message<String>
     */
    List<Message<Integer>> deleteTag(List<Integer> tagList);

    /**
     * 获取此标签下的用户数量
     *
     * @param tagID 标签ID
     * @return Message<Integer>
     */
    Message<Integer> getUserCount(int tagID);

    /**
     * 获取此标签下的用户数量
     *
     * @param tagID       标签ID
     * @param next_openid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return Message<Integer>
     */
    Message<Integer> getUserCount(int tagID, String next_openid);


    /**
     * 获取此标签下的所有用户信息
     *
     * @param tagID 标签ID
     * @return Message<List<User>>
     */
    Message<List<UserMessage>> getUserList(int tagID);

    /**
     * 获取此标签下的所有用户信息
     *
     * @param tagID       标签ID
     * @param next_openid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return Message<List<User>>
     */
    Message<List<UserMessage>> getUserList(int tagID, String next_openid);

    /**
     * 获取此标签下所有用户的OPENID
     *
     * @param tagID 标签ID
     * @return Message<List<String>>
     */
    Message<List<String>> getUserOpenIDList(int tagID);

    /**
     * 获取此标签下所有用户的OPENID
     *
     * @param tagID       标签ID
     * @param next_openid 第一个拉取的OPENID，不填默认从头开始拉取
     * @return Message<List<String>>
     */
    Message<List<String>> getUserOpenIDList(int tagID, String next_openid);
}
