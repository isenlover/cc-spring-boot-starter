package pers.cc.spring.api.nim.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.nim.annotation.EnableNim;
import pers.cc.spring.api.nim.enums.NimUrl;
import pers.cc.spring.api.nim.exception.NIMChatroomCreateException;
import pers.cc.spring.api.nim.exception.NIMUserCreateException;
import pers.cc.spring.api.nim.model.NIMUser;
import pers.cc.spring.api.nim.model.chatroom.ChatroomCreateDTO;
import pers.cc.spring.api.nim.model.chatroom.ChatroomCreateResp;
import pers.cc.spring.api.nim.model.team.NIMTeamAddUserDTO;
import pers.cc.spring.api.nim.model.team.NIMTeamDTO;
import pers.cc.spring.api.nim.model.team.vo.NIMTeamVO;
import pers.cc.spring.api.nim.service.NIMService;
import pers.cc.spring.api.nim.util.NIMHelper;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.http.HttpUtils;

/**
 * com.cc.api.netease.service.impl
 *
 * @author chengce
 * @version 2017-12-27 17:47
 */
@Service
@ConditionalOnBean(annotation = EnableNim.class)
public class NIMServiceImpl implements NIMService {

  @Autowired
  private NIMHelper nimHelper;

  public Message<NIMUser> createNIMUser(String accid) throws NIMUserCreateException {
    return createNIMUser(accid, null, null);
  }

  public Message<NIMUser> createNIMUser(String accid, String name) throws NIMUserCreateException {
    return createNIMUser(accid, name, null);
  }

  @Override
  public Message<NIMUser> updateNIMUserNickname(String accid, String name) {
    String resp;
    try {
      NIMUser nimUser = new NIMUser();
      nimUser.setAccid(accid);
      nimUser.setName(name);
      resp = HttpUtils.httpsPost(NimUrl.UPDATE_USER.getDescription()
          , HttpUtils.getUrlParamsByObject(nimUser)
          , nimHelper.getHttpHeaders());
    } catch (Exception e) {
      throw new NIMUserCreateException();
    }
    return nimHelper.checkResponse(resp, NIMUser.class);
  }

  public Message<NIMUser> createNIMUser(String accid, String name, String token) throws NIMUserCreateException {
    NIMUser nimUser = new NIMUser();
    nimUser.setAccid(accid);
    nimUser.setName(name);
    nimUser.setToken(token);
    return createNIMUser(nimUser);
  }

  public Message<NIMUser> createNIMUser(NIMUser nimUser) throws NIMUserCreateException {
    String resp;
    try {
      resp = HttpUtils.httpsPost(NimUrl.CREATE_USER.getDescription()
          , HttpUtils.getUrlParamsByObject(nimUser)
          , nimHelper.getHttpHeaders());
    } catch (Exception e) {
      throw new NIMUserCreateException();
    }
    return nimHelper.checkResponse(resp, NIMUser.class);
  }

  @Override
  public Message<ChatroomCreateResp> createChatroom(ChatroomCreateDTO chatroomCreateDTO) throws NIMChatroomCreateException {
    String resp;
    try {
      resp = HttpUtils.httpsPost(NimUrl.CREATE_CHAT_ROOM.getDescription()
          , HttpUtils.getUrlParamsByObject(chatroomCreateDTO)
          , nimHelper.getHttpHeaders());
    } catch (Exception e) {
      throw new NIMChatroomCreateException();
    }
    return nimHelper.checkResponse(resp, ChatroomCreateResp.class);
  }

  @Override
  public Message<NIMTeamVO> createTeam(NIMTeamDTO nimTeamDTO) {
    String resp;
    try {
      resp = HttpUtils.httpsPost(NimUrl.CREATE_TEAM.getDescription()
          , HttpUtils.getUrlParamsByObject(nimTeamDTO)
          , nimHelper.getHttpHeaders());
    } catch (Exception e) {
      throw new NIMChatroomCreateException();
    }
    return Message.ok(JSON.parseObject(resp, NIMTeamVO.class));
//    return nimHelper.checkResponse(resp, NIMTeamVO.class);
  }

  @Override
  public Message<Void> addUserToTeam(NIMTeamAddUserDTO addUserDTO) {
    String resp;
    try {
      resp = HttpUtils.httpsPost(NimUrl.JOIN_TEAM.getDescription()
          , HttpUtils.getUrlParamsByObject(addUserDTO)
          , nimHelper.getHttpHeaders());
    } catch (Exception e) {
      throw new NIMChatroomCreateException();
    }
    return nimHelper.checkResponse(resp);
  }
}
