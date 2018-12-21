package com.jd.union.wechat.api.message.mass;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * OpenID群发消息的基类
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class MPBaseMsg {
	// OpenID列表,最多10000个
	@Getter @Setter protected List<String> touser;
	// 群发的消息类型,图文:mpnews/文本:text/语音:voice/音乐:music/图片:image/视频:video 
	@Getter @Setter protected String msgtype;
	
}
