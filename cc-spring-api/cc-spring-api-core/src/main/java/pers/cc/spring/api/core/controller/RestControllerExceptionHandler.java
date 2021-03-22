package pers.cc.spring.api.core.controller;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import pers.cc.spring.api.core.model.ParamNotValidDTO;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.core.message.MessageCode;
import pers.cc.spring.core.properties.CoreProperties;
import pers.cc.spring.log.elk.enums.ELKLogStatus;
import pers.cc.spring.log.elk.enums.ELKLogType;
import pers.cc.spring.log.elk.model.ELKLogBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

import static pers.cc.spring.core.message.MessageCode.BAD_REQUEST_PARAM;


/**
 * 捕获controller全局异常
 *
 * @author chengce
 * @version 2017-10-18 16:47
 */
@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

  @Autowired
  ELKLogBuilder elkLogBuilder;

  @Autowired
  CoreProperties coreProperties;

  private void writeLog(Exception e, HttpServletRequest request) {
    if (coreProperties.isDebug()) {
      e.printStackTrace();
    }
    log.error(JSON.toJSONString(
        elkLogBuilder.build(ELKLogType.EXCEPTION)
            .className("GlobalExceptionController")
            .description("服务器Exception异常，：" + "错误信息：" + e.getMessage())
            .exception(e.toString())
            .stackTrace(Arrays.toString(e.getStackTrace()))
            .status(ELKLogStatus.FAILURE)
            .build()
    ));
  }

  private void writeLog(String errors, HttpServletRequest request) {
    log.error(JSON.toJSONString(
        elkLogBuilder.build(ELKLogType.EXCEPTION).className("GlobalExceptionController")
            .description("服务器MethodArgumentNotValidException异常【传入参数不合法造成】")
            .exception(errors)
            .status(ELKLogStatus.FAILURE)
            .build()
    ));
  }

  /**
   * 捕获全局异常
   *
   * @param e 异常
   */
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<Message> handleException(Exception e, HttpServletRequest request) {
    writeLog(e, request);
    Message.Builder builder = Message.failed().messageCode(MessageCode.SERVER_ERROR);
    if (coreProperties.isDebug()) {
      builder.message(e.getLocalizedMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builder.build());
  }

  /**
   * 捕获URL请求错误异常
   *
   * @param e 异常
   */
  @ExceptionHandler(value = NoHandlerFoundException.class)
  public ResponseEntity<Message> handleNoHandlerFoundException(Exception e, HttpServletRequest request) {
    writeLog(e, request);
    Message.Builder builder = Message.failed().messageCode(MessageCode.SERVER_ERROR_URL_ERROR);
    if (coreProperties.isDebug()) {
      builder.message(e.getLocalizedMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builder.build());
  }

  /**
   * 表单传入controller转化出错，例如枚举收到非法值
   *
   * @param e 异常
   */
  @ExceptionHandler(value = HttpMessageNotReadableException.class)
  public ResponseEntity<Message> handleHttpMessageNotReadableException(Exception e, HttpServletRequest request) {
    writeLog(e, request);
    Message.Builder builder = Message.failed().messageCode(MessageCode.SERVER_ERROR_PARAM_PARSE_ERROR);
    if (coreProperties.isDebug()) {
      builder.message(e.getLocalizedMessage());
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builder.build());
  }

//  /**
//   * 索引唯一存储报错
//   *
//   * @param e 异常
//   */
//  @ExceptionHandler(value = {DataIntegrityViolationException.class})
//  public ResponseEntity<Message> handleSQLIntegrityConstraintViolationExceptionException(Exception e, HttpServletRequest request) {
//    writeLog(e, request);
//    Message.Builder builder = Message.failed().messageCode(MessageCode.BAD_REQUEST_CREATE_ERROR_UNIQUE_KEY);
//    if (coreProperties.isDebug()) {
//      builder.message(e.getLocalizedMessage());
//    }
//    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(builder.build());
//  }

  /**
   * 捕获自定义runtime异常
   *
   * @param e 自定义runtime异常
   */
  @ExceptionHandler(value = BaseRuntimeException.class)
  public ResponseEntity<Message> handleBaseException(BaseRuntimeException e) {
    return ResponseEntity.status(e.getStatusCode())
        .body(Message.failed().message(e.getMessage()).code(e.getErrCode()).build());
  }

  /**
   * 捕获jwt token过期异常
   *
   * @param e jwt token过期异常
   */
  @ExceptionHandler(value = ExpiredJwtException.class)
  public ResponseEntity<Message> handleExpiredJwtException(ExpiredJwtException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
        Message.failed().messageCode(MessageCode.UNAUTHORIZED_EXPIRED).build());
  }

  /**
   * 捕获spring security 验证用户名密码时异常
   *
   * @param e UsernameNotFoundException
   */
  @ExceptionHandler(value = UsernameNotFoundException.class)
  public ResponseEntity<Message> handleBaseException(UsernameNotFoundException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Message.failed().messageCode(MessageCode.BAD_REQUEST_USERNAME_ERROR).build());
  }

  /**
   * 捕获controller传入参数不合法的异常，模型对象 @Validated
   *
   * @param exception 异常
   * @see javax.validation.Valid
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                              HttpServletRequest request) {
    BindingResult bindingResult = exception.getBindingResult();
    List<ParamNotValidDTO> errors = new ArrayList<>();
    Set<String> codes = new HashSet<>();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      if (codes.contains(fieldError.getField())) {
        continue;
      }
      codes.add(fieldError.getField());
      errors.add(new ParamNotValidDTO.Builder()
          .field(fieldError.getField())
          .message(fieldError.getDefaultMessage())
          .build());
    }
    String json = JSON.toJSONString(errors);
    writeLog(json, request);
    return ResponseEntity.badRequest().body(Message.failed()
        .messageCode(BAD_REQUEST_PARAM)
        .message(errors.get(0).getMessage())
        .errorFields(json)
        .build()
    );
  }

  /**
   * 捕获参数异常，传参 @requestParam
   *
   * @param e 异常
   * @see org.springframework.validation.annotation.Validated
   */
  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity<Message> handleConstraintViolationException(ConstraintViolationException e) {
    Set<String> fields = new HashSet<>();
    List<ParamNotValidDTO> errors = new ArrayList<>();
    for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
      String path = constraintViolation.getPropertyPath().toString();
      path = path.substring(path.lastIndexOf(".") + 1);
      if (!fields.contains(path)) {
        errors.add(new ParamNotValidDTO.Builder()
            .field(path)
            .message(constraintViolation.getMessage())
            .build());
      }
      fields.add(path);
    }
    String json = JSON.toJSONString(errors);
    return ResponseEntity.badRequest().body(Message.failed()
        .messageCode(BAD_REQUEST_PARAM)
        .message(errors.get(0).getMessage())
        .errorFields(json)
        .build()
    );
  }

}
