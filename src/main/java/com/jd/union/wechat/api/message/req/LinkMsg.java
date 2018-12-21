package com.jd.union.wechat.api.message.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 链接消息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class LinkMsg extends BaseMsg {
	// 消息标题
	@Getter @Setter private String Title;
	// 消息描述
	@Getter @Setter private String Description;
	// 消息链接
	@Getter @Setter private String Url;

}
