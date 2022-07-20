package pers.cc.spring.security.jwt.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.cc.spring.security.jwt.model.JwtUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author chengce
 * @version 2018-05-18 20:22
 */
public class JwtFactory {

  /**
   * 生成jwtUser
   *
   * @param id                   主键id
   * @param username             用户名
   * @param lastPasswordRestDate 最后更新密码日期
   * @return jwtUser
   */
  public static JwtUser getJwtUser(String id, String username, Date lastPasswordRestDate) {
    return new JwtUser.Builder()
        .id(id)
        .username(username)
        .lastPasswordResetDate(lastPasswordRestDate)
        .build();
  }

  public static boolean isValidPassword(String password, String databasePassword) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.matches(password, databasePassword);
  }

  public static String getEncryptPassword(String password) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.encode(password);
  }

  public static HttpServletRequest getRequest() {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    return requestAttributes.getRequest();
  }
}
