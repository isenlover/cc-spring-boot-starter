package pers.cc.spring.api.aliyun.alipay.annotation;

import org.springframework.context.annotation.Import;
import pers.cc.spring.api.aliyun.alipay.config.AlipayImport;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2021-06-14 22:54
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AlipayImport.class)
public @interface EnableAliPay {

}
