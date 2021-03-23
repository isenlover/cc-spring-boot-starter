package pers.cc.spring.security.jwt.service.impl;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.security.jwt.model.JwtSecurityParamBean;
import pers.cc.spring.security.jwt.model.JwtUser;
import pers.cc.spring.security.jwt.service.JwtService;
import pers.cc.spring.security.jwt.util.JwtFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * jwt+spring-security token验证工具包
 *
 * @author chengce
 * @version 2017-10-08 01:24
 */
@Service
@ConditionalOnBean(JwtSecurityParamBean.class)
public class JwtServiceImpl implements JwtService {

  private final long serialVersionUID = -3301605591108950415L;

  private final String CLAIM_KEY_USERNAME = "sub";
  private final String CLAIM_KEY_USER = "user";
  private final String CLAIM_KEY_USER_AUTHORITIES = "userAuthorities";
  private final String CLAIM_KEY_CREATED = "created";

  @Autowired
  JwtSecurityParamBean jwtSecurityParamBean;
  private final String secret = "c(*$@#(*$(Hfefweof*#)#c";

  /**
   * 生成token
   *
   * @param claims 键值对
   * @return 返回token
   */
  private String generatorToken(Map<String, Object> claims) {
    return Jwts.builder()
        .setClaims(claims)
        .setExpiration(generatorExpirationDate())
        .signWith(SignatureAlgorithm.HS512, secret)
        .compact();
  }

  /**
   * 获取token的创建日期
   *
   * @param token token
   * @return 日期
   */
  private Date getCreatedDateFromToken(String token) {
    Date created;
    try {
      final Claims claims = getClaimsFromToken(token);
      created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
    } catch (Exception e) {
      throw e;
    }
    return created;
  }

  /**
   * 获取token的过期日期
   *
   * @param token token
   * @return 日期
   */
  private Date getExpirationDateFromToken(String token) {
    Date expiration;
    try {
      final Claims claims = getClaimsFromToken(token);
      expiration = claims.getExpiration();
    } catch (Exception e) {
      throw e;
    }
    return expiration;
  }

  /**
   * 解密token
   *
   * @param token token
   * @return map
   */
  private Claims getClaimsFromToken(String token) {
    if (token == null) {
      return null;
    }
    Claims claims;
    try {
      claims = Jwts.parser()
          .setSigningKey(secret)
          .parseClaimsJws(token)
          .getBody();
    } catch (Exception e) {
      throw e;
    }
    return claims;
  }

  /**
   * 生成token过期日期
   *
   * @return 日期
   */
  private Date generatorExpirationDate() {
    return new Date(System.currentTimeMillis() + jwtSecurityParamBean.getExpiryTime() * 1000L);
  }

  /**
   * token是否过期
   *
   * @param token token
   * @return 是否过期
   * @deprecated Token获取时会自动检验
   */
  @Deprecated
  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  /**
   * 是否已经修改过密码
   *
   * @param created           创建日期
   * @param lastPasswordReset 最后的修改密码日期
   * @return 是否修改过密码
   */
  private Boolean isCreatedBeforeLastPasswordReset(Date created,
                                                   Date lastPasswordReset) {
    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  public Boolean canTokenBeRefreshed(String token,
                                     Date lastPasswordReset) {
    final Date created = getCreatedDateFromToken(token);
    return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
        && !isTokenExpired(token);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String authHeader = request.getHeader(jwtSecurityParamBean.getHttpHeader());
    if (authHeader != null && authHeader.startsWith(jwtSecurityParamBean.getTokenHead())) {
      return authHeader.substring(jwtSecurityParamBean.getTokenHead().length());
    } else {
      throw new JwtException("token解析异常");
    }
  }

  @Override
  public String refreshToken(HttpServletRequest request) {
    String token = getTokenFromRequest(request);
    String refreshedToken;
    try {
      final Claims claims = getClaimsFromToken(token);
      claims.put(CLAIM_KEY_CREATED, new Date());
      refreshedToken = generatorToken(claims);
    } catch (Exception e) {
      throw e;
    }
    return refreshedToken;
  }

  @Override
  public String createToken(JwtUser jwtUser) {
    Map<String, Object> claims = new HashMap<>();
    claims.put(CLAIM_KEY_USER, jwtUser);
    claims.put(CLAIM_KEY_USER_AUTHORITIES, JSON.toJSONString(jwtUser.getAuthorities()));
    claims.put(CLAIM_KEY_CREATED, new Date());
    return jwtSecurityParamBean.getTokenHead() + this.generatorToken(claims);
  }

  @Override
  public String createToken(String id, String account, Date lastPasswordResetDate) {
    return createToken(JwtFactory.getJwtUser(id, account, lastPasswordResetDate));
  }

  @Override
  public String createToken(String id, String account) {
    return createToken(id, account, null);
  }

  @Override
  public JwtUser getUserInfo(HttpServletRequest request) {
    String authHeader = request.getHeader(jwtSecurityParamBean.getHttpHeader());
    if (authHeader != null && authHeader.startsWith(jwtSecurityParamBean.getTokenHead())) {
      String token = authHeader.substring(jwtSecurityParamBean.getTokenHead().length());
      return getUserInfo(token);
    } else {
      return null;
    }
  }

  @Override
  public JwtUser getUserInfo() throws JwtException {
    return getUserInfo(JwtFactory.getRequest());
  }

  @Override
  public JwtUser getUserInfo(String token) throws JwtException {
    try {
      final Claims claims = getClaimsFromToken(token);
      Map<String, Object> data = (Map<String, Object>) claims.get(CLAIM_KEY_USER);
      JwtUser jwtUser = ClassUtils.mapToObject(data, JwtUser.class);
      List<SimpleGrantedAuthority> simpleGrantedAuthorities = JSON.parseArray(
          (String) claims.get(CLAIM_KEY_USER_AUTHORITIES), SimpleGrantedAuthority.class);
      jwtUser.setAuthorities(simpleGrantedAuthorities);
      return jwtUser;
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public Boolean removeToken(String token) {
    try {
      final Claims claims = getClaimsFromToken(token);
      claims.setExpiration(new Date());
    } catch (Exception e) {
      throw e;
    }
    return true;
  }

  @Override
  public Boolean removeToken(HttpServletRequest request) {
    return removeToken(getTokenFromRequest(request));
  }

  @Override
  public Boolean validateToken(String token,
                               UserDetails userDetails) {
    JwtUser user = (JwtUser) userDetails;
    final JwtUser jwtUser = getUserInfo(token);
    final Date created = getCreatedDateFromToken(token);
    return jwtUser.getUsername().equals(user.getUsername())
//        && !isTokenExpired(token)
        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
  }
}
