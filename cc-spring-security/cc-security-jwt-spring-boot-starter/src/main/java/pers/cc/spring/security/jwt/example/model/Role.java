package pers.cc.spring.security.jwt.example.model;

/**
 * com.cc.security.jwt.example.pers.cc.cfootball.common.model
 * <p>
 * 权限中的角色类
 *
 * @author chengce
 * @version 2017-10-05 13:56
 */
public class Role {
    private String id;

    private String roleName;

    public Role() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
