package pers.cc.spring.api.wechat.model.menu;

import pers.cc.spring.api.wechat.model.other.WxBaseResponse;

/**
 * @author chengce
 * @version 2016-11-18 12:03
 */
public class MenuMessage extends WxBaseResponse {
    private Menu menu;
    private ConditionalMenu[] conditionalmenu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public ConditionalMenu[] getConditionalmenu() {
        return conditionalmenu;
    }

    public void setConditionalmenu(ConditionalMenu[] conditionalmenu) {
        this.conditionalmenu = conditionalmenu;
    }
}
