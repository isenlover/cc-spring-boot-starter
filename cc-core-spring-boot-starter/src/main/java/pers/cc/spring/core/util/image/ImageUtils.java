package pers.cc.spring.core.util.image;

import org.apache.commons.io.FileUtils;
import org.springframework.util.Base64Utils;
import pers.cc.spring.core.exception.ImageRuntimeException;
import pers.cc.spring.core.message.MessageCode;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.other.MathUtils;

import java.io.File;

/**
 * 图像工具类
 *
 * @author chengce
 * @version 2018-01-02 22:59
 */
public class ImageUtils {
  /**
   * base64data转换成图片文件
   *
   * @param base64Url base64data
   * @param savePath  保存的路径
   * @return 生成的file
   * @throws ImageRuntimeException 异常
   */
  public static File base64DataTransformImageFile(String base64Url, String savePath) throws ImageRuntimeException {
    String dataPrefix;
    String data;
    File file;
    if (base64Url == null || "".equals(base64Url)) {
      throw new ImageRuntimeException(MessageCode.SERVER_ERROR_BASE64);
    } else {
      String[] dataArray = base64Url.split("base64,");
      if (dataArray.length == 2) {
        dataPrefix = dataArray[0];
        data = dataArray[1];
      } else {
        throw new ImageRuntimeException(MessageCode.SERVER_ERROR_BASE64);
      }
    }
    String suffix;
    // data:image/jpeg;base64,base64编码的jpeg图片数据
    if ("data:image/jpeg;".equalsIgnoreCase(dataPrefix)) {
      suffix = ".jpg";
      // data:image/x-icon;base64,base64编码的icon图片数据
    } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrefix)) {
      suffix = ".ico";
      // data:image/gif;base64,base64编码的gif图片数据
    } else if ("data:image/gif;".equalsIgnoreCase(dataPrefix)) {
      suffix = ".gif";
      // data:image/png;base64,base64编码的png图片数据
    } else if ("data:image/png;".equalsIgnoreCase(dataPrefix)) {
      suffix = ".png";
    } else {
      throw new ImageRuntimeException(MessageCode.SERVER_ERROR_BASE64);
    }
    String tempFileName = MathUtils.getSimpleUUID() + suffix;

    //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
    byte[] bs = Base64Utils.decodeFromString(data);
    try {
      //使用apache提供的工具类操作流
      if (CommonUtils.isNotEmpty(savePath)) {
        savePath += "/";
      }
      file = new File(savePath + tempFileName);
      FileUtils.writeByteArrayToFile(file, bs);
    } catch (Exception e) {
      throw new ImageRuntimeException("base64图片保存失败", MessageCode.SERVER_ERROR_BASE64);
    }
    return file;
  }
}
