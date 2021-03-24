package pers.cc.spring.security.jwt.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author chengce
 * @version 2021-03-24 22:23
 */
@Component
@ConditionalOnMissingBean(UserDetailsService.class)
public class JwtUserDetailsServiceImpl implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return null;
  }
}
