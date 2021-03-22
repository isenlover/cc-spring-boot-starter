package pers.cc.spring.security.jwt.example.utl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * com.cc.security.jwt.example.utl
 *
 * @author chengce
 * @version 2017-10-08 02:04
 */
public class JwtUserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过数据库或其他方式获取到用户然后封装返回
        return null;
//        User user = userRepository.findByAccountAndUserType(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("用户名不存在");
//        } else {
//            return JwtUserFactory.create(user);
//        }
    }
}
