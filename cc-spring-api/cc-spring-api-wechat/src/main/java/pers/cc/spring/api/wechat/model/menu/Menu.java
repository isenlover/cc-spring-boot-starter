package pers.cc.spring.api.wechat.model.menu;

import lombok.Data;
import pers.cc.spring.api.wechat.model.other.WxBaseResponse;

/**
 * Created by CC
 * Date: 2016-07-01
 * Time: 16:13
 */
@Data
public class Menu extends WxBaseResponse {
    private Button[] button;

    private String menuid;
    public Menu() {

    }

    public Menu(String menuid) {
        this.menuid = menuid;
    }

    public Menu(Button[] button, String menuid) {
        this.button = button;
        this.menuid = menuid;
    }
}
