package pers.cc.spring.security.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.cc.spring.security.jwt.filter.JwtAuthenticationTokenFilter;
import pers.cc.spring.security.jwt.model.JwtSecurityParamBean;

/**
 * 基于token形式的security配置
 *
 * @author chengce
 * @version 2017-10-08 03:08
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnBean(UserDetailsService.class)
public class JwtSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  JwtSecurityParamBean jwtSecurityParamBean;

  @Autowired
  public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder
        // 设置UserDetailsService
        .userDetailsService(userDetailsService)
        // 使用BCrypt进行密码的hash
        .passwordEncoder(passwordEncoder());
  }

  /**
   * 装载BCrypt密码编码器
   *
   * @return 加密器
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
    return new JwtAuthenticationTokenFilter();
  }


  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        // 由于使用的是JWT，我们这里不需要csrf
        .csrf().ignoringAntMatchers("/ws/**").disable()
//                .cors().and()

        // 基于token，所以不需要session
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
//        .httpBasic()
//        .exceptionHandling()
//        .authenticationEntryPoint(new CAuthenticationEntryPoint())
//        .accessDeniedHandler(new CAccessDeniedHandler())
//        .and()
        .authorizeRequests()
        // 允许对于网站静态资源的无授权访问
        .antMatchers(
            HttpMethod.GET,
            "/",
//            "/error",
            "/*.html",
            "/favicon.ico",
            "/**/*.html",
            "/**/*.css",
            "/**/*.png",
            "/**/*.jpg",
            "/**/*.js",
            "/**/*.txt"
        ).permitAll()
//                .antMatchers("/swagger-ui.html").access("cc")
        .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
        // 对于获取token的rest api要允许匿名访问
        // FIXME: 2018/6/18 common
        // /csrf 是swagger的一个访问地址  2021.1.22
        .antMatchers(jwtSecurityParamBean.getPermitRequests()).permitAll()
//        .antMatchers("/auth/**", "/api/**/auth/**", "/api/**/socket/**", "/api/**/common/**", "/socket/**", "/csrf", "/").permitAll()
//        // swagger
//        .antMatchers("/swagger-resources/**", "/v2/api-docs/**", "/webjars/springfox-swagger-ui/**").permitAll()
        // websocket
        .antMatchers("/ws/**").permitAll()
        // 除上面外的所有请求全部需要鉴权认证
        .anyRequest()
        .authenticated();

    // 添加JWT filter
    httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    // 禁用缓存
    httpSecurity.headers().cacheControl();
  }

}
