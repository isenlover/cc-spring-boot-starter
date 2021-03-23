package pers.cc.spring.api.aliyun.oss.bean;

import lombok.Data;

/**
 * @author chengce
 * @version 2018-06-14 22:51
 */
@Data
public class AliyunOSSParamBean {

  private String intranetEndpoint;

  private String internetEndpoint;

  private String bucketName;

  private String url;

  private String cdnUrl;
}
