package pers.cc.spring.api.nim.service;

import pers.cc.spring.api.nim.exception.NIMUserCreateException;
import pers.cc.spring.api.nim.model.NIMUser;
import pers.cc.spring.api.nim.model.chatroom.ChatroomCreateDTO;
import pers.cc.spring.api.nim.model.chatroom.ChatroomCreateResp;
import pers.cc.spring.api.nim.model.team.NIMTeamAddUserDTO;
import pers.cc.spring.api.nim.model.team.NIMTeamDTO;
import pers.cc.spring.api.nim.model.team.vo.NIMTeamVO;
import pers.cc.spring.core.message.Message;

/**
 * com.cc.api.netease.service
 *
 * @author chengce
 * @version 2017-12-27 17:47
 */
public interface NIMService {

  /**
   * 创建云信账号 (返回网易生成的token)
   *
   * @param accid 云信id
   * @return 创建状态和账号详情
   * @throws NIMUserCreateException 创建用户异常
   */
  Message<NIMUser> createNIMUser(String accid);

  /**
   * 创建云信账号 (返回网易生成的token)
   *
   * @param accid 云信id
   * @param name  昵称
   * @return 创建状态和账号详情
   * @throws NIMUserCreateException 创建用户异常
   */
  Message<NIMUser> createNIMUser(String accid, String name);

  /**
   * 创建云信账号
   *
   * @param accid 云信id
   * @param name  昵称
   * @param token token值
   * @return 创建状态和账号详情
   * @throws NIMUserCreateException 创建用户异常
   */
  Message<NIMUser> createNIMUser(String accid, String name, String token);

  /**
   * 创建云信账号
   *
   * @param nimUser 云信账号
   * @return 创建状态和账号详情
   * @throws NIMUserCreateException 创建用户异常
   */
  Message<NIMUser> createNIMUser(NIMUser nimUser);

  /**
   * 创建聊天室
   *
   * @param chatroomCreateDTO 聊天室
   * @return 聊天室信息
   */
  Message<ChatroomCreateResp> createChatroom(ChatroomCreateDTO chatroomCreateDTO);

  Message<NIMTeamVO> createTeam(NIMTeamDTO nimTeamDTO);

  /**
   * 拉人入群
   * @param addUserDTO
   * @return
   */
  Message<Void> addUserToTeam(NIMTeamAddUserDTO addUserDTO);
}
