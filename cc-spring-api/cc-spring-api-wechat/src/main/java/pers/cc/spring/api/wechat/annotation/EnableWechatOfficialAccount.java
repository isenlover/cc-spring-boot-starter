package pers.cc.spring.api.wechat.annotation;

import org.springframework.context.annotation.Import;
import pers.cc.spring.api.wechat.config.WechatConfigurationImport;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-01 17:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(WechatConfigurationImport.class)
public @interface EnableWechatOfficialAccount {
}
