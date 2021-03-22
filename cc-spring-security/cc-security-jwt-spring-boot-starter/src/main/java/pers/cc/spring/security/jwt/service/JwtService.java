package pers.cc.spring.security.jwt.service;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.UserDetails;
import pers.cc.spring.security.jwt.model.JwtUser;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Date;


/**
 * Jwt接口
 *
 * @author chengce
 * @version 2017-10-10 17:12
 */
public interface JwtService {
  /**
   * 验证Token合法性
   *
   * @param token       token
   * @param userDetails userDetails
   * @return 是否合法
   */
  Boolean validateToken(String token,
                        UserDetails userDetails) throws AccessDeniedException;

  /**
   * 刷新token
   *
   * @param request 旧token
   * @return 新token
   */
  String refreshToken(HttpServletRequest request);

  /**
   * 创建token
   *
   * @param jwtUser 用户信息
   * @return token
   */
  String createToken(JwtUser jwtUser);

  /**
   * 创建token
   *
   * @return token
   */
  String createToken(String id, String account, Date lastPasswordResetDate);

  /**
   * 创建token
   *
   * @return token
   */
  String createToken(String id, String account);

  /**
   * 从token中获取用户信息
   *
   * @param request 请求
   * @return 用户信息
   * @throws JwtException token异常
   */
  JwtUser getUserInfo(HttpServletRequest request);

  /**
   * 从token中获取用户信息
   *
   * @return 用户信息
   * @throws JwtException token异常
   */
  JwtUser getUserInfo() throws JwtException;

  /**
   * 从token中获取用户信息
   *
   * @param token 凭证
   * @return 用户信息
   * @throws JwtException token异常
   */
  JwtUser getUserInfo(String token) throws JwtException;

  /**
   * 移除token
   *
   * @param token token
   * @return 是否已移除
   */
  Boolean removeToken(String token);

  /**
   * 移除token
   *
   * @param request http请求
   * @return 是否已移除
   */
  Boolean removeToken(HttpServletRequest request);
}
