package pers.cc.spring.api.nim.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import pers.cc.spring.api.nim.annotation.EnableNim;
import pers.cc.spring.api.nim.enums.NimUrl;
import pers.cc.spring.api.nim.exception.NIMUserCreateException;
import pers.cc.spring.api.nim.model.team.NIMTeamHistoryDTO;
import pers.cc.spring.api.nim.model.team.vo.NIMHistoryContentVO;
import pers.cc.spring.api.nim.service.NIMHistoryService;
import pers.cc.spring.api.nim.util.NIMHelper;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.util.http.HttpUtils;

import java.util.List;

/**
 * @author chengce
 * @version 2021-04-16 20:53
 */
@Service
@ConditionalOnBean(annotation = EnableNim.class)
public class NIMHistoryServiceImpl implements NIMHistoryService {

  @Autowired
  NIMHelper nimHelper;

  @Override
  public <T> Message<List<NIMHistoryContentVO<T>>> getTeamHistoryMessages(NIMTeamHistoryDTO teamHistory, Class<T> clazz) {
    String resp;
    try {
      resp = HttpUtils.httpsPost(NimUrl.QUERY_TEAM_MSG_HISTORY.getDescription()
          , HttpUtils.getUrlParamsByObject(teamHistory)
          , nimHelper.getHttpHeaders());
    } catch (Exception e) {
      throw new NIMUserCreateException();
    }
    return nimHelper.getHistoryResponse(resp, clazz);
  }
}
