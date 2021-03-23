package pers.cc.spring.api.aliyun.oss.annotation;

import java.lang.annotation.*;

/**
 * @author chengce
 * @version 2018-06-14 22:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AliyunOSSParam {

  /**
   * 内网阿里云endpoint
   */
  String intranetEndpoint() default "";

  /**
   * 外网阿里云endpoint
   */
  String internetEndpoint() default "";

  /**
   * 阿里云bucket
   */
  String bucketName();

  /**
   * 自定义上传后的url
   */
  String url();

  /**
   * 自定义上传后的url
   */
  String cdnUrl() default "";
}
