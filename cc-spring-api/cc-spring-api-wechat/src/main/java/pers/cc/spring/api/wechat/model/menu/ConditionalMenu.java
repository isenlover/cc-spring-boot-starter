package pers.cc.spring.api.wechat.model.menu;

/**
 * Created by CC on 2016-11-14 17:36
 */
public class ConditionalMenu extends Menu{
    private MatchRule matchrule;

    public MatchRule getMatchrule() {
        return matchrule;
    }

    public void setMatchrule(MatchRule matchrule) {
        this.matchrule = matchrule;
    }
}
