package pers.cc.spring.api.wechat.enums;

/**
 * Created by iSen on 2016/11/4
 */
public enum WechatMenuType {
    Click("click"),

    View("view"),

    Location("location_select")
    ;

    String typeName;

    WechatMenuType(String type) {
        this.typeName = type;
    }

    public String getString() {
        return this.typeName;
    }
}
