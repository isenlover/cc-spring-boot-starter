package pers.cc.spring.log.elk.model;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.http.HttpUtils;
import pers.cc.spring.log.elk.enums.ELKLogType;
import pers.cc.spring.security.jwt.model.JwtUser;
import pers.cc.spring.security.jwt.service.JwtService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 创建日志基本信息工具
 *
 * @author chengce
 * @version 2018-05-10 11:21
 */
@Component
public class ELKLogBuilder {

  @Autowired(required = false)
  JwtService jwtService;

  /**
   * 创建日志builder
   *
   * @param joinPoint 切面点
   * @return build
   */
  public Log.Builder build(JoinPoint joinPoint, ELKLogType elkLogType) {
    // 类名
    String className = joinPoint.getTarget().getClass().getName();
    Signature signature = joinPoint.getSignature();
    MethodSignature methodSignature = (MethodSignature) signature;
    // 方法名
    String methodName = methodSignature.getName();
    // 方法参数
    String[] paramNames = methodSignature.getParameterNames();
    // 方法参数值
    Object[] paramValues = joinPoint.getArgs();
    // 组装方法和参数
    String method = methodName + "(" + Arrays.toString(paramNames)
        .replace("[", "")
        .replace("]", "") + ")";
    String params = Arrays.toString(paramValues);
    params = params.substring(0, Math.min(params.length(), 500000));

    return build(elkLogType)
        .className(className)
        .method(method)
        .paramValues(params);
  }

  /**
   * 获取基本的日志信息
   *
   * @return 日志builder
   */
  public Log.Builder build(ELKLogType elkLogType) {
    return build(elkLogType, true);
  }

  /**
   * 获取基本的日志信息
   *
   * @return 日志builder
   */
  public Log.Builder build(ELKLogType elkLogType, boolean isJoinUser) {
    Log.Builder builder = new Log.Builder().type(elkLogType);
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (requestAttributes != null) {
      HttpServletRequest httpServletRequest = requestAttributes.getRequest();
      // 获取用户ip
      String ip = HttpUtils.getIpAddress(httpServletRequest);
      String requestPath = httpServletRequest.getRequestURI();
      // 获取操作系统和浏览器信息
      HttpUtils.OsBrowser osBrowser = HttpUtils.getOsAndBrowserInfo(httpServletRequest);
      builder.userIp(ip)
          .operatingSystem(osBrowser.getOs())
          .browser(osBrowser.getBrowser())
          .requestPath(requestPath);
      if (isJoinUser && jwtService != null) {
        JwtUser jwtUser = jwtService.getUserInfo(httpServletRequest);
        if (CommonUtils.isNotEmpty(jwtUser)) {
          builder.userId(jwtUser.getId())
              .username(jwtUser.getUsername());
        }
      }
    }
    return builder;
  }
}
