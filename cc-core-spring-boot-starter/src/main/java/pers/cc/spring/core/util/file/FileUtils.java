package pers.cc.spring.core.util.file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件相关工具
 *
 * @author chengce
 * @version 2018-04-30 16:49
 */
@Slf4j
public class FileUtils {
  /**
   * 创建文件,并会自动创建目录
   *
   * @param file 需要创建的文件
   * @return 是否创建成功
   */
  public static boolean createOrUpdateFile(File file) {
    if (file == null) return false;
    if (!file.isDirectory() && !file.exists()) {
      String fileName = file.getName();
      try {
        if (fileName.contains(".")) {
          File filePath = new File(file.getPath().replaceAll(fileName, ""));
          if (!filePath.exists()) {
            return filePath.mkdirs() && file.createNewFile();
          } else {
            return file.createNewFile();
          }
        } else {
          return file.mkdirs();
        }
      } catch (IOException e) {
        return false;
      }
    } else {
      return true;
    }
  }

  /**
   * @see FileUtils#getFileExt(File)
   */
  public static String getFileExt(File file) {
    return getFileExt(file.getName());
  }

  /**
   * 获取文件扩展名
   *
   * @param fileName 文件
   * @return 扩展名 带.
   */
  public static String getFileExt(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
  }


  public static void bytesToFile(byte[] bytes, String outFilePath) {
    File outFile = new File(outFilePath);
    createOrUpdateFile(outFile);
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(outFile);
      fileOutputStream.write(bytes);
      fileOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
