package pers.cc.spring.api.aliyun.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.cc.spring.api.aliyun.common.annotation.AliyunParam;
import pers.cc.spring.api.aliyun.common.bean.AliyunParamBean;
import pers.cc.spring.api.aliyun.common.exception.AliyunRuntimeException;
import pers.cc.spring.api.aliyun.common.properties.AliyunProperties;
import pers.cc.spring.api.aliyun.oss.annotation.AliyunOSSParam;
import pers.cc.spring.api.aliyun.oss.bean.AliyunOSSParamBean;
import pers.cc.spring.api.aliyun.oss.service.AliyunOSSService;
import pers.cc.spring.core.exception.BaseRuntimeException;
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
@Slf4j
@Primary
@Service
@ConditionalOnBean(annotation = {AliyunOSSParam.class, AliyunParam.class})
public class AliyunOSSImpl implements AliyunOSSService {
  @Resource
  AliyunOSSParamBean aliyunOSSParamBean;
  
  @Resource
  AliyunParamBean aliyunParamBean;

  @Autowired
  AliyunProperties aliyunProperties;

  private OSSClient ossClient;

  public OSSClient ossClient() {
    return ossClient == null ? (ossClient = new OSSClient(getEndpoint(), aliyunParamBean.getKey(), aliyunParamBean.getSecret())) : ossClient;
  }

  private String getEndpoint() {
    return !aliyunProperties.isDebug() ? aliyunOSSParamBean.getIntranetEndpoint() : aliyunOSSParamBean.getInternetEndpoint();
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
      if (aliyunProperties.isDebug()) {
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
    if (CommonUtils.isNotEmpty(aliyunOSSParamBean.getCdnUrl())) {
      url = aliyunOSSParamBean.getCdnUrl();
    } else {
      url = aliyunOSSParamBean.getUrl();
    }
    try {
      ossClient().putObject(new PutObjectRequest(aliyunOSSParamBean.getBucketName(), key, new FileInputStream(file)));
      if (delete) {
        file.deleteOnExit();
      }
    } catch (FileNotFoundException e) {
      if (aliyunProperties.isDebug()) {
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
    return ossClient().doesObjectExist(aliyunOSSParamBean.getBucketName(), url);
  }

  @Override
  public void deleteFile(String url) {
    ossClient().deleteObject(aliyunOSSParamBean.getBucketName(), url);
  }
}
