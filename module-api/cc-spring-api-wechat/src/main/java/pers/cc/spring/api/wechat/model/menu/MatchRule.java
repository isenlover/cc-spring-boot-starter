package pers.cc.spring.api.wechat.model.menu;

/**
 * 菜单匹配规则(自定义菜单)
 * Created by CC on 2016-11-14 17:36
 */
public class MatchRule {
    // 用户标签的id，可通过用户标签管理接口获取
    private int tag_id;

    private int group_id;
    // 性别：男（1）女（2），不填则不做匹配
    private Integer sex;
    // 国家信息
    private String country;
    // 省份信息
    private String province;
    // 城市信息
    private String city;
    // 客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配
    private String client_platform_type;
    // 1、简体中文 "zh_CN" 2、繁体中文TW "zh_TW" 3、繁体中文HK "zh_HK" 4、英文 "en" 5、印尼 "id" 6、马来 "ms" 7、西班牙 "es" 8、韩国 "ko" 9、意大利 "it" 10、日本 "ja" 11、波兰 "pl" 12、葡萄牙 "pt" 13、俄国 "ru" 14、泰文 "th" 15、越南 "vi" 16、阿拉伯语 "ar" 17、北印度 "hi" 18、希伯来 "he" 19、土耳其 "tr" 20、德语 "de" 21、法语 "fr"
    private String language;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getTag_id() {
        return tag_id;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getClient_platform_type() {
        return client_platform_type;
    }

    public void setClient_platform_type(String client_platform_type) {
        this.client_platform_type = client_platform_type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
