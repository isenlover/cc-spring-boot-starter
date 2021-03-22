package pers.cc.spring.api.wechat.model.js;

import lombok.Data;
import pers.cc.spring.core.annotation.typescript.Typescript;

/**
 * js前端需要的配置参数
 *
 * @author chengce
 * @version 2017-11-14 00:59
 */
@Data
@Typescript
public class WxJsConfigParamVO {
    private String appId;

    private String timestamp;

    private String nonceStr;

    private String signature;
}
