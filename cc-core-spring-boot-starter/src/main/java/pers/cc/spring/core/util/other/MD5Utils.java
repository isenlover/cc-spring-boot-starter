package pers.cc.spring.core.util.other;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5加密工具
 *
 * @author chengce
 * @version 2018-01-08 13:18
 */
@Slf4j
public class MD5Utils {

  private static String byteArrayToHexString(byte b[]) {
    StringBuffer resultSb = new StringBuffer();
    for (byte aB : b) {
      resultSb.append(byteToHexString(aB));
    }

    return resultSb.toString();
  }

  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n += 256;
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  /**
   * 返回32位加密代码
   *
   * @param origin      原始代码
   * @param charsetname utf-8
   * @return 加密后代码
   */
  public static String getMD5(String origin, String charsetname) {
    String resultString = null;
    try {
      resultString = origin;
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(origin.getBytes(StandardCharsets.UTF_8));
      if (charsetname == null || "".equals(charsetname)) {
        resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
      } else {
        resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
      }
    } catch (Exception ignored) {
    }
    return resultString;
  }

  public static String getMD5(String origin) {
    return getMD5(origin, "UTF-8");
  }

  private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
      "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}
