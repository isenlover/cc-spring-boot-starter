package pers.cc.spring.api.core.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.cc.spring.api.core.model.BasicErrorMapper;
import pers.cc.spring.core.message.MessageCode;
import pers.cc.spring.core.util.other.ClassUtils;
import pers.cc.spring.log.elk.enums.ELKLogStatus;
import pers.cc.spring.log.elk.enums.ELKLogType;
import pers.cc.spring.log.elk.model.ELKLogBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chengce
 * @version 2017-10-31 23:15
 * @link https://blog.csdn.net/caplike/article/details/106331643
 */
@Slf4j
@RestController
public class BasicErrorHandler extends BasicErrorController {
  @Autowired
  ELKLogBuilder elkLogBuilder;

  public BasicErrorHandler(ErrorAttributes errorAttributes,
                           ErrorProperties errorProperties) {
    super(errorAttributes, errorProperties);
  }

  public BasicErrorHandler(ErrorAttributes errorAttributes,
                           ErrorProperties errorProperties,
                           List<ErrorViewResolver> errorViewResolvers) {
    super(errorAttributes, errorProperties, errorViewResolvers);
  }

  public BasicErrorHandler() {
    super(new DefaultErrorAttributes(), new ErrorProperties());
  }

  private static final String PATH = "/error";

  /**
   * 处理全局系统异常
   *
   * @param request request
   * @return map
   * @see BasicErrorMapper
   * @see pers.cc.spring.core.message
   */
  @Override
  @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
//    Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
    Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
    HttpStatus status = getStatus(request);
    BasicErrorMapper basicErrorMapper = ClassUtils.mapToObject(body, BasicErrorMapper.class);
    log.error(JSON.toJSONString(
        elkLogBuilder.build(ELKLogType.SERVE_ERROR, true)
            .className("BasicErrorHandler")
            .method("error")
            .description(
                "服务器发生error，状态码：" + basicErrorMapper.getStatus() + "，错误信息：" + basicErrorMapper.getError())
            .exception(basicErrorMapper.getError() + basicErrorMapper.getException())
            .status(ELKLogStatus.FAILURE)
            .requestPath(basicErrorMapper.getPath())
            .build()
    ));
    switch (status) {
      case FORBIDDEN:
        basicErrorMapper.messageCode(MessageCode.FORBIDDEN_PERMISSION);
        break;
      case NOT_FOUND:
        basicErrorMapper.messageCode(MessageCode.PAGE_NOT_FOUND);
        break;
      case INTERNAL_SERVER_ERROR:
//                if (basicErrorMapper.getException() != null) {
        // jjwt token登录过期
//                    if (basicErrorMapper.getException().equals(ExpiredJwtException.class.getName())) {
//                        basicErrorMapper.message(MessageCode.FORBIDDEN_EXPIRED);
//                    }
        // spring-security 用户名或密码出错
//                    if (basicErrorMapper.getException().equals(UsernameNotFoundException.class.getName())) {
//                        basicErrorMapper.message(MessageCode.FORBIDDEN_USERNAME_ERROR);
//                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
//                                ConvertUtils.objectToMap(basicErrorMapper));
//                    }
//                } else {
        basicErrorMapper.messageCode(MessageCode.SERVER_ERROR);
//                }
        break;
      default: {
        basicErrorMapper.messageCode(MessageCode.SERVER_ERROR);
      }
    }
    basicErrorMapper.setException(null);
    basicErrorMapper.setStatus(null);
    return ResponseEntity.status(status).body(ClassUtils.objectToMap(basicErrorMapper));
  }

}
