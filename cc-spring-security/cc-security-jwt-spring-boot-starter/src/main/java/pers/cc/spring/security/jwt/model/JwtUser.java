package pers.cc.spring.security.jwt.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * jwt 用户基类
 *
 * @author chengce
 * @version 2017-10-07 22:11
 */
public class JwtUser implements UserDetails {

    private String id;
    private String username;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;
    private final Date lastPasswordResetDate;

    public JwtUser() {
        this.id = "";
        this.username = "";
        this.password = "";
        this.authorities = null;
        this.lastPasswordResetDate = null;
    }

    public JwtUser(
            String id,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Date lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public JwtUser(String id,
                   String username,
                   String password,
                   Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.lastPasswordResetDate = null;
    }

    private JwtUser(Builder builder) {
        setId(builder.id);
        setUsername(builder.username);
        password = builder.password;
        setAuthorities(builder.authorities);
        lastPasswordResetDate = builder.lastPasswordResetDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    /**
     * 返回分配给用户的角色列表
     *
     * @return 角色列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getSimpleAuthority() {
        if (this.authorities != null && this.authorities.size() == 1) {
            return this.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0);
        }
        return null;
    }

    @JSONField(serialize = false)
    public String getId() {
        return id;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 账户是否未过期
     *
     * @return 账户是否未过期
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     *
     * @return 账户是否未锁定
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     *
     * @return 密码是否未过期
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否激活
     *
     * @return 账户是否激活
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 这个是自定义的，返回上次密码重置日期
     *
     * @return 上次密码重置日期
     */
    @JSONField(serialize = false)
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }


    public static final class Builder {
        private String id;
        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;
        private Date lastPasswordResetDate;

        public Builder() {
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder authorities(Collection<? extends GrantedAuthority> val) {
            authorities = val;
            return this;
        }

        public Builder lastPasswordResetDate(Date val) {
            lastPasswordResetDate = val;
            return this;
        }

        public JwtUser build() {
            return new JwtUser(this);
        }
    }
}
