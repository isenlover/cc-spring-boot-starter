package pers.cc.spring.api.nim.model.team.vo;

import lombok.Data;

import java.util.List;

/**
 * @author chengce
 * @version 2021-04-16 20:46
 */
@Data
public class NIMHistoryBaseVO<T> {
  private int code;

  private List<NIMHistoryContentVO<T>> msgs;
}
