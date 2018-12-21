package com.jd.union.wechat.api.message.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信事件父类
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class BaseEvent {
	// 开发者微信号
	@Getter @Setter protected String ToUserName;
	// 发送方帐号（一个OpenID）
	@Getter @Setter protected String FromUserName;
	// 消息创建时间 （整型）
	@Getter @Setter protected long CreateTime;
	// 消息类型，event
	@Getter @Setter protected String MsgType;
	// 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
	@Getter @Setter protected String Event;

}
