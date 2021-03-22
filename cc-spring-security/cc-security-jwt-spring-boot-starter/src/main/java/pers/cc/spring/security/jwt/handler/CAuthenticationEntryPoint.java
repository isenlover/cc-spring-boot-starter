package pers.cc.spring.security.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import pers.cc.spring.core.exception.BaseRuntimeException;
import pers.cc.spring.security.jwt.exception.AccountAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @author chengce
 * @version 2021-03-08 17:05
 */
@Slf4j
@Deprecated
public class CAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AuthenticationException e) throws IOException, ServletException {
//    log.info("");
//    if(e instanceof AccountAuthenticationException) {
//      httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
//    }
//    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
//    HashMap<String, String> map = new HashMap<>(2);
//    map.put("uri", httpServletRequest.getRequestURI());
//    map.put("msg", "认证失败");
//    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//    httpServletResponse.setCharacterEncoding("utf-8");
//    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
//    ObjectMapper objectMapper = new ObjectMapper();
//    String resBody = objectMapper.writeValueAsString(map);
//    PrintWriter printWriter = httpServletResponse.getWriter();
//    printWriter.print(resBody);
//    printWriter.flush();
//    printWriter.close();
  }
}
