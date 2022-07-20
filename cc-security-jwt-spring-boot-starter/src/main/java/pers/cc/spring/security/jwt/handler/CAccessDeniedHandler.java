package pers.cc.spring.security.jwt.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengce
 * @version 2021-03-08 17:05
 */
@Slf4j
@Deprecated
public class CAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest httpServletRequest,
                     HttpServletResponse httpServletResponse,
                     AccessDeniedException e) throws IOException, ServletException {
    log.info("");
  }
}
