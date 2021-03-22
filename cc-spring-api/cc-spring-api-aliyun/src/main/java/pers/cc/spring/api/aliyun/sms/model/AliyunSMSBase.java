package pers.cc.spring.api.aliyun.sms.model;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import pers.cc.spring.core.util.other.DateUtils;

import java.util.concurrent.TimeUnit;

/**
 * 阿里云短信基础类
 *
 * @author chengce
 * @version 2018-01-11 16:01
 */
public class AliyunSMSBase {
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 短信回复类
     */
    private SendSmsResponse sendSmsResponse;

    public AliyunSMSBase() {
    }

    public AliyunSMSBase(String mobile, SendSmsResponse sendSmsResponse) {
        this.mobile = mobile;
        this.timestamp = DateUtils.getTimestamp(TimeUnit.SECONDS);
        this.sendSmsResponse = sendSmsResponse;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public SendSmsResponse getSendSmsResponse() {
        return sendSmsResponse;
    }

    public void setSendSmsResponse(SendSmsResponse sendSmsResponse) {
        this.sendSmsResponse = sendSmsResponse;
    }
}
