package pers.cc.spring.api.aliyun.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.multipart.MultipartFile;
import pers.cc.spring.api.aliyun.bean.AliyunInformation;
import pers.cc.spring.api.aliyun.bean.AliyunOSSInformation;
import pers.cc.spring.api.aliyun.exception.AliyunRuntimeException;
import pers.cc.spring.api.aliyun.oss.annotation.AliyunOSSApp;
import pers.cc.spring.api.aliyun.oss.service.AliyunOssService;
import pers.cc.spring.api.aliyun.property.AliyunProperties;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.properties.CoreProperties;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.file.FileUtils;
import pers.cc.spring.core.util.image.ImageUtils;
import pers.cc.spring.core.util.other.MathUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author chengce
 * @version 2017-10-19 22:59
 */
@ConditionalOnBean(annotation = AliyunOSSApp.class)
public class AliyunOssImpl implements AliyunOssService {
  @Resource
  AliyunInformation aliyunInformation;

  @Resource
  AliyunOSSInformation aliyunOSSInformation;

  @Autowired
  CoreProperties coreProperties;

  @Autowired
  AliyunProperties aliyunProperties;

  private OSSClient ossClient;

//  @Bean
  public OSSClient ossClient() {
    return ossClient == null ? (ossClient = new OSSClient(getEndpoint(), aliyunInformation.getKey(), aliyunInformation.getSecret())) : ossClient;
  }

  private String getEndpoint() {
    return aliyunProperties.isProduction() ? aliyunOSSInformation.getIntranetEndpoint() : aliyunOSSInformation.getInternetEndpoint();
  }

  public String uploadFile(MultipartFile multipartFile) {
    if (multipartFile == null) {
      throw new BaseRuntimeException("文件为空");
    }
    String fileName = multipartFile.getOriginalFilename();
//    String path;
//    if (coreProperties.isDebug()) {
//      path = "tempUploadFile/";
//    } else {
//      path = HttpUtils.getHttpServletRequest().getSession().getServletContext().getRealPath("").concat("/tempUploadFile/");
//    }
    //上传文件
    if (CommonUtils.isEmpty(fileName)) {
      throw new AliyunRuntimeException("文件名读取失败");
    }
//    File targetFile = new File(path, fileName);
    try {
      File targetFile = File.createTempFile(MathUtils.getSimpleUUID(), FileUtils.getFileExt(fileName));
      multipartFile.transferTo(targetFile);
//      if (!targetFile.exists()) {
//        if (!targetFile.mkdirs()) {
//          throw new AliyunRuntimeException("文件保存失败");
//        }
//      }
      return uploadFile(targetFile);
    } catch (IOException e) {
      if (coreProperties.isDebug()) {
        e.printStackTrace();
      }
      throw new AliyunRuntimeException("文件保存失败");
    }
  }

  @Override
  public List<String> uploadFile(List<MultipartFile> files) {
    if (CommonUtils.isEmpty(files)) {
      return new ArrayList<>();
    }
    List<String> urls = new ArrayList<>();
    for (MultipartFile file : files) {
      urls.add(uploadFile(file));
    }
    return urls;
  }

  @Override
  public String uploadFile(File file) {
    return uploadFile(file, true);
  }

  @Override
  public String uploadFile(File file, boolean delete) {
    String key = UUID.randomUUID().toString().replaceAll("-", "") + FileUtils.getFileExt(file);
    String url;
    if (CommonUtils.isNotEmpty(aliyunOSSInformation.getCdnUrl())) {
      url = aliyunOSSInformation.getCdnUrl();
    } else {
      url = aliyunOSSInformation.getUrl();
    }
    try {
      ossClient().putObject(new PutObjectRequest(aliyunOSSInformation.getBucketName(), key, new FileInputStream(file)));
      if (delete) {
        file.deleteOnExit();
      }
    } catch (FileNotFoundException e) {
      if (coreProperties.isDebug()) {
        e.printStackTrace();
      }
      throw new AliyunRuntimeException("文件保存失败");
    }
    return url + "/" + key;
  }

  @Override
  public String uploadFile(String base64Data) {
    return uploadFile(ImageUtils.base64DataTransformImageFile(base64Data, ""));
  }

  @Override
  public String uploadFile(String base64Data, boolean delete) {
    return uploadFile(ImageUtils.base64DataTransformImageFile(base64Data, ""), delete);
  }

  @Override
  public boolean checkExist(String url) {
    return ossClient().doesObjectExist(aliyunOSSInformation.getBucketName(), url);
  }

  @Override
  public void deleteFile(String url) {
    ossClient().deleteObject(aliyunOSSInformation.getBucketName(), url);
  }
}
