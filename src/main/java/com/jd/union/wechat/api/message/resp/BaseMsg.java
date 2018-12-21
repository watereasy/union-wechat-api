package com.jd.union.wechat.api.message.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信回复消息父类
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class BaseMsg {
	// 开发者微信号
	@Getter @Setter protected String ToUserName;
	// 发送方帐号（一个OpenID）
	@Getter @Setter protected String FromUserName;
	// 消息创建时间 （整型）
	@Getter @Setter protected long CreateTime;
	// 消息类型
	@Getter @Setter protected String MsgType;

}
