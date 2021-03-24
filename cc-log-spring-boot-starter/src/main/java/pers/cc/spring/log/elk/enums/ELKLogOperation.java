package pers.cc.spring.log.elk.enums;


/**
 * 日志操作类型
 *
 * @author chengce
 * @version 2018-04-13 12:13
 */
public enum ELKLogOperation {
  /**
   * 新增
   */
  INSERT,
  /**
   * 删除
   */
  DELETE,
  /**
   * 更新
   */
  UPDATE,
  /**
   * 查询
   */
  SELECT,
  /**
   * 系统判断
   */
  AUTO,
}
