package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分组群发消息的基类
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class MGBaseMsg {
	// 用于设定图文消息的接收者
	@Getter @Setter protected MGFilter filter;
	// 群发的消息类型,图文:mpnews/文本:text/语音:voice/音乐:music/图片:image/视频:video 
	@Getter @Setter protected String msgtype;

}
