package pers.cc.spring.api.aliyun.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * 阿里云Oss业务接口
 *
 * @author chengce
 * @version 2017-10-19 23:18
 */
public interface AliyunOSSService {

  String uploadFile(MultipartFile file);

  List<String> uploadFile(List<MultipartFile> files);

  String uploadFile(File file);

  String uploadFile(File file, boolean delete);

  String uploadFile(String base64Data);

  String uploadFile(String base64Data, boolean delete);

  boolean checkExist(String url);

  void deleteFile(String url);
}
