package pers.cc.spring.security.jwt.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.message.MessageCode;
import pers.cc.spring.security.jwt.model.JwtUser;
import pers.cc.spring.security.jwt.model.JwtSecurityParamBean;
import pers.cc.spring.security.jwt.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * com.cc.jwt.security.jwt
 *
 * @author chengce
 * @version 2017-10-08 03:10
 */
@Slf4j
@Component
@ConditionalOnBean(UserDetailsService.class)
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private JwtSecurityParamBean jwtSecurityParamBean;

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    setResponse(httpServletResponse);
    String requestURI = httpServletRequest.getRequestURI();
    if (Arrays.stream(jwtSecurityParamBean.getPermitRequests())
        .map(s -> s.replace("**", ""))
        .noneMatch(requestURI::contains)) {
      try {
        String authHeader = httpServletRequest.getHeader(jwtSecurityParamBean.getHttpHeader());
        if (authHeader != null && authHeader.startsWith(jwtSecurityParamBean.getTokenHead())) {
          final String authToken = authHeader.substring(jwtSecurityParamBean.getTokenHead().length());
          JwtUser jwtUser = jwtService.getUserInfo(httpServletRequest);
          if (jwtUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUser.getId());
            if (userDetails != null && jwtService.validateToken(authToken, userDetails)) {
              UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                  userDetails.getAuthorities());
              authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
              SecurityContextHolder.getContext().setAuthentication(authentication);
            }
          }
        }
      } catch (Exception e) {
        processException(e, httpServletResponse);
        return;
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private void processException(Exception exception, HttpServletResponse httpServletResponse) throws IOException {
    httpServletResponse.setContentType("application/json,charset=UTF-8");
    httpServletResponse.setCharacterEncoding("UTF-8");
    if (exception instanceof BaseRuntimeException) {
      BaseRuntimeException baseRuntimeException = (BaseRuntimeException) exception;
      httpServletResponse.setStatus(baseRuntimeException.getStatusCode());
      httpServletResponse.getWriter().write(
          JSON.toJSONString(Message.failed().message(baseRuntimeException.getMessage()).code(baseRuntimeException.getErrCode()).build()));
    } else if (exception instanceof AuthenticationException) {
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write(JSON.toJSONString(Message.failed().messageCode(MessageCode.UNAUTHORIZED).build()));
    } else if (exception instanceof ExpiredJwtException) {
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write(JSON.toJSONString(Message.failed().messageCode(MessageCode.UNAUTHORIZED_EXPIRED).build()));
    } else {
      httpServletResponse.getWriter().write(JSON.toJSONString(Message.failed().messageCode(MessageCode.SERVER_ERROR).build()));
    }
    // TODO: 2021/3/8 可以写elk日志
  }

  /**
   * 跨域官方推荐
   * 设置了这个才能在filter里throw到前端报错
   *
   * @param response
   * @see pers.cc.spring.security.jwt.config.CorsConfig
   * @deprecated 此跨域方式存在缺陷：websocket跨域不能解决
   */
  @Deprecated
  private void setResponse(HttpServletResponse response) {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "*");
  }
}
