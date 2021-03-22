package pers.cc.spring.log.elk.enums;

/**
 * @author chengce
 * @version 2018-04-13 12:07
 */
public enum ELKLogStatus {
    /**
     * 方法准备执行
     */
    BEFORE_EXECUTE,
    /**
     * 执行返回成功
     */
    SUCCESS,
    /**
     * 执行返回失败
     */
    FAILURE,
}
