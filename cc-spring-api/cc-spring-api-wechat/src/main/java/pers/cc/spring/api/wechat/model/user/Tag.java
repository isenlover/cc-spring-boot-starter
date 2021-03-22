package pers.cc.spring.api.wechat.model.user;

/**
 * Created by CC on 2016-11-14 17:55
 */
public class Tag {
    // 微信生成的标签id
    private int id;

    // 标签名字
    private String name;

    // 此标签下的粉丝数
    private int count;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(int id) {
        this.id = id;
    }

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
