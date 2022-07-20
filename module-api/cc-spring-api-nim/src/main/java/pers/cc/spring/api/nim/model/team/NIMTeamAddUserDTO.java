package pers.cc.spring.api.nim.model.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author chengce
 * @version 2021-04-26 22:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NIMTeamAddUserDTO {

  private String tid;

  private String owner;

  private List<String> members;

  private int magree;

  private String msg;
  
  private String attach;

}
