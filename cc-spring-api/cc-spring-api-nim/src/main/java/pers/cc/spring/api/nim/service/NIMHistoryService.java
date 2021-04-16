package pers.cc.spring.api.nim.service;

import pers.cc.spring.api.nim.model.team.NIMTeamHistoryDTO;
import pers.cc.spring.api.nim.model.team.vo.NIMHistoryContentVO;
import pers.cc.spring.core.message.Message;

import java.util.List;

/**
 * @author chengce
 * @version 2021-04-16 20:45
 */
public interface NIMHistoryService {

  <T> Message<List<NIMHistoryContentVO<T>>> getTeamHistoryMessages(NIMTeamHistoryDTO teamHistory, Class<T> clazz);
}
