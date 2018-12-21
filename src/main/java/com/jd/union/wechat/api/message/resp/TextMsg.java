package com.jd.union.wechat.api.message.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文本消息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class TextMsg extends BaseMsg {
	// 回复的消息内容
	@Getter @Setter private String Content;

}
