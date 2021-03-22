package pers.cc.spring.api.wechat.model.response;

import lombok.Data;

@Data
public class Music {
	// 音乐名称  
    private String Title;  
    // 音乐描述  
    private String Description;  
    // 音乐链接  
    private String MusicUrl;  
    // 高质量音乐链接，WIFI环境优先使用该链接播放音乐  
    private String HQMusicUrl;  
}
