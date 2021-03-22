package pers.cc.spring.security.jwt.example.model;

import java.util.List;

/**
 * com.cc.security.jwt.example.pers.cc.cfootball.common.model
 * <p>
 * 用户类
 *
 * @author chengce
 * @version 2017-10-10 17:28
 */
public class User {
    private String id;
    private String account;
    private String password;

    // 权限列表
    private List<Role> roleList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
