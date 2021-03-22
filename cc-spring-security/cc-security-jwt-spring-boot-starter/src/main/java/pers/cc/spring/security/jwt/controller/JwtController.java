package pers.cc.spring.security.jwt.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.cc.spring.core.message.Message;
import pers.cc.spring.security.jwt.service.JwtService;

import javax.annotation.security.DenyAll;
import javax.servlet.http.HttpServletRequest;

/**
 * jwt中token controller
 * 示例controller
 * 需要项目自己实现其实方法
 *
 * @author chengce
 * @version 2018-02-01 13:12
 */
@Api("Token相关接口")
@RestController
@RequestMapping("${api.version}/token")
public class JwtController {
    @Autowired
    JwtService jwtService;


    @DenyAll
    @ApiOperation("刷新token")
    @PutMapping
    public ResponseEntity refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(Message.ok(jwtService.refreshToken(request)));
    }

    @DenyAll
    @ApiOperation("移除token")
    @DeleteMapping
    public ResponseEntity removeToken(HttpServletRequest request) {
        return ResponseEntity.ok(Message.ok(jwtService.removeToken(request)));
    }
}
