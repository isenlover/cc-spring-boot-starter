package pers.cc.spring.api.nim.model.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创建群组
 *
 * @author chengce
 * @version 2021-04-26 15:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NIMTeamDTO {
  /*required*/
  private String tname;

  /*required*/
  private String owner;

  /*required*/
  private List<String> members;

  private String announcement;

  private String intro;

  /*required*/
  private String msg;

  /*管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群。其它会返回414*/
  private Integer magree;

  /*群建好后，sdk操作时，0不用验证，1需要验证,2不允许任何人加入。其它返回414*/
  private Integer joinmode;

  private String custom;

  private String icon;

  private Integer beinvitemode;

  private Integer invitemode;

  private Integer uptinfomode;

  private Integer upcustommode;

  private Integer teamMemberLimit;
}
