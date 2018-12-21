package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群发结果
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class MassResult {
	// 错误码 
	@Getter @Setter private int errCode;
	// 错误信息 
	@Getter @Setter private String errMsg;
	// 消息ID 
	@Getter @Setter private int msgId;
	// 消息发送后的状态，SEND_SUCCESS表示发送成功
	@Getter @Setter private String msgStatus;

}
