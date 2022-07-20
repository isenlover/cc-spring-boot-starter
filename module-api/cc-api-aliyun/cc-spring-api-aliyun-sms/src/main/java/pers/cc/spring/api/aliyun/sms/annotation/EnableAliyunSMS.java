package pers.cc.spring.api.aliyun.sms.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Import;
import pers.cc.spring.api.aliyun.common.annotation.AliyunParam;
import pers.cc.spring.api.aliyun.common.annotation.EnableAliyunCommon;
import pers.cc.spring.api.aliyun.sms.config.AliyunSMSImport;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-14 22:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AliyunSMSImport.class)
@EnableAliyunCommon
@ConditionalOnBean(annotation = AliyunParam.class)
public @interface EnableAliyunSMS {

}
