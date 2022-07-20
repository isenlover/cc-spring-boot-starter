package pers.cc.spring.api.nim.model.team.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.cc.spring.api.nim.model.response.NIMBaseResponse;

/**
 * 群组响应
 * @author chengce
 * @version 2021-04-26 15:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NIMTeamVO extends NIMBaseResponse {

  private String tid;
}