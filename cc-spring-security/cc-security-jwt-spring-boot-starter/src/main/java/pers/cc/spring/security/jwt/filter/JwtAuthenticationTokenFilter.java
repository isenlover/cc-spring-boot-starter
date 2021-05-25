package pers.cc.spring.security.jwt.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.message.MessageCode;
import pers.cc.spring.core.properties.CoreProperties;
import pers.cc.spring.security.jwt.model.JwtSecurityParamBean;
import pers.cc.spring.security.jwt.model.JwtUser;
import pers.cc.spring.security.jwt.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * com.cc.jwt.security.jwt
 *
 * @author chengce
 * @version 2017-10-08 03:10
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private JwtSecurityParamBean jwtSecurityParamBean;

  @Autowired
  CoreProperties coreProperties;

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    setResponse(httpServletResponse, httpServletRequest);
    String requestURI = httpServletRequest.getRequestURI();
    if (requestURI.equals("/csrf") || requestURI.equals("/")) {
      return;
    }
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
    Message.Builder<Object> failedBuilder = Message.failed();
    if (exception instanceof BaseRuntimeException) {
      BaseRuntimeException baseRuntimeException = (BaseRuntimeException) exception;
      httpServletResponse.setStatus(baseRuntimeException.getStatusCode());
      httpServletResponse.getWriter().write(
          JSON.toJSONString(failedBuilder.message(baseRuntimeException.getMessage()).code(baseRuntimeException.getErrCode()).build()));
    } else if (exception instanceof AuthenticationException || exception instanceof SignatureException) {
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write(JSON.toJSONString(failedBuilder.messageCode(MessageCode.UNAUTHORIZED).build()));
    } else if (exception instanceof ExpiredJwtException) {
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write(JSON.toJSONString(failedBuilder.messageCode(MessageCode.UNAUTHORIZED_EXPIRED).build()));
    } else {
      if (coreProperties.isDebug()) {
        failedBuilder.message("测试环境：" + exception.getLocalizedMessage());
      } else {
        failedBuilder.messageCode(MessageCode.SERVER_ERROR);
      }
      httpServletResponse.getWriter().write(JSON.toJSONString(failedBuilder.build()));
    }
    // TODO: 2021/3/8 可以写elk日志
  }

  /**
   * 跨域官方推荐
   * 设置了这个才能在filter里throw到前端报错
   *
   * @deprecated 此跨域方式存在缺陷：websocket跨域不能解决
   */
  @Deprecated
  private void setResponse(HttpServletResponse response, HttpServletRequest httpServletRequest) {
    String origin = httpServletRequest.getHeader("Origin");
    AtomicReference<String> allowOrigin = new AtomicReference<>();
    if (!coreProperties.isDebug()) {
      allowOrigin.set("null");
      if (origin != null) {
        jwtSecurityParamBean.getAllowOrigins().stream().filter(origin::equals).findFirst().ifPresent(allowOrigin::set);
      }
    } else {
      allowOrigin.set("*");
    }
    log.info("跨域地址：" + allowOrigin.get());
    response.setHeader("Access-Control-Allow-Origin", allowOrigin.get());
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "*");
  }
}
