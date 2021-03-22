package pers.cc.spring.api.wechat.model.response;


import lombok.Data;
import pers.cc.spring.api.wechat.enums.WechatMessageType;

@Data
public class MusicMessage extends BaseMessage {
	// 音乐  
    private Music Music;

    public MusicMessage() {
        this.setMsgType(WechatMessageType.response_music.getType());
    }
}
