package pers.cc.spring.log.elk.model;

import lombok.Data;
import pers.cc.spring.log.elk.ELKLogAspect;
import pers.cc.spring.log.elk.enums.ELKLogStatus;
import pers.cc.spring.log.elk.enums.ELKLogType;

/**
 * 日志基本信息
 * 此类记录日志的一些基本字段，大部分字段都可以为空
 * 根据不同场景记录需要的值
 *
 * @author chengce
 * @version 2018-04-30 16:31
 * @see ELKLogAspect
 */
@Data
public class Log {
    /**
     * 日志操作类型
     */
    private String operation;

    /**
     * 日志状态
     */
    private ELKLogStatus status;

    /**
     * 日志类型
     */
    private ELKLogType type;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 账号
     */
    private String username;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 用户地区
     */
    private String userArea;

    /**
     * 日志的简单描述
     */
    private String description;

    /**
     * 触发日志的类名
     */
    private String className;

    /**
     * 触发日志的方法
     */
    private String method;

    /**
     * 触发日志的参数值集合
     */
    private String paramValues;

    /**
     * 任务执行时间
     */
    private long taskTime;

    /**
     * 方法的返回值【如果存在】
     */
    private String returns;

    /**
     * 日志异常信息
     */
    private String exception;

    /**
     * 定位异常
     */
    private String stackTrace;

    /**
     * 请求的url
     */
    private String requestPath;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String operatingSystem;

    public Log() {
    }

    private Log(Builder builder) {
        setOperation(builder.operation);
        setStatus(builder.status);
        setType(builder.type);
        setUserId(builder.userId);
        setUsername(builder.username);
        setUserIp(builder.userIp);
        setUserArea(builder.userArea);
        setDescription(builder.description);
        setClassName(builder.className);
        setMethod(builder.method);
        setParamValues(builder.paramValues);
        setTaskTime(builder.taskTime);
        setReturns(builder.returns);
        setException(builder.exception);
        setStackTrace(builder.stackTrace);
        setRequestPath(builder.requestPath);
        setBrowser(builder.browser);
        setOperatingSystem(builder.operatingSystem);
    }

    public static final class Builder {
        private String operation;
        private ELKLogStatus status;
        private ELKLogType type;
        private String userId;
        private String username;
        private String userIp;
        private String userArea;
        private String description;
        private String className;
        private String method;
        private String paramValues;
        private long taskTime;
        private String returns;
        private String exception;
        private String stackTrace;
        private String requestPath;
        private String browser;
        private String operatingSystem;

        public Builder() {
        }

        public Builder operation(String val) {
            operation = val;
            return this;
        }

        public Builder status(ELKLogStatus val) {
            status = val;
            return this;
        }

        public Builder type(ELKLogType val) {
            type = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder userIp(String val) {
            userIp = val;
            return this;
        }

        public Builder userArea(String val) {
            userArea = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder className(String val) {
            className = val;
            return this;
        }

        public Builder method(String val) {
            method = val;
            return this;
        }

        public Builder paramValues(String val) {
            paramValues = val;
            return this;
        }

        public Builder taskTime(long val) {
            taskTime = val;
            return this;
        }

        public Builder returns(String val) {
            returns = val;
            return this;
        }

        public Builder exception(String val) {
            exception = val;
            return this;
        }

        public Builder stackTrace(String val) {
            stackTrace = val;
            return this;
        }

        public Builder requestPath(String val) {
            requestPath = val;
            return this;
        }

        public Builder browser(String val) {
            browser = val;
            return this;
        }

        public Builder operatingSystem(String val) {
            operatingSystem = val;
            return this;
        }

        public Log build() {
            return new Log(this);
        }
    }
}
