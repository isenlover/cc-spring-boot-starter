package pers.cc.spring.data.redis.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.cc.spring.core.message.MessageCode;
import pers.cc.spring.core.util.CommonUtils;
import pers.cc.spring.core.util.http.HttpUtils;
import pers.cc.spring.data.redis.exception.RequestLimitRuntimeException;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 请求限制切面实现
 *
 * @author chengce
 * @version 2017-07-04 12:42
 */
@Slf4j
@Aspect
@Component
public class RequestLimitAspect {
  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Before(value = "execution(* *(..)) && @annotation(limit)")
  public void requestLimit(JoinPoint joinpoint, RequestLimit limit) {
    // 接收到请求，记录请求内容
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();

    final String ip = HttpUtils.getIpAddressOld(request);
//        log.info("getIpAddressOld：" + ip);
//        log.info("getIpAddress：" + HttpUtils.getIpAddress(request));
    final String url = request.getRequestURL().toString();
    final String key = "req_limit_".concat(url).concat(ip);

    //加1后看看值
    Long count = redisTemplate.opsForValue().increment(key, 1);
    if (count == null) {
      log.warn("请求限制切面已失效，原因：redis缓存异常");
    } else {
      //刚创建
      if (count == 1) {
        //设置1分钟过期
        redisTemplate.expire(key, limit.time(), TimeUnit.SECONDS);
      }
      if (count > limit.count()) {
        if (CommonUtils.isEmpty(limit.message())) {
          throw new RequestLimitRuntimeException(MessageCode.BAD_REQUEST_REQUEST_LIMIT);
        }
        throw new RequestLimitRuntimeException(limit.message(), MessageCode.BAD_REQUEST_REQUEST_LIMIT);
      }
    }
  }
}
