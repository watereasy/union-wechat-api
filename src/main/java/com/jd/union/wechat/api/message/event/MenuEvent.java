package com.jd.union.wechat.api.message.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 自定义菜单事件
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class MenuEvent extends BaseEvent {
	// 事件KEY值，与自定义菜单接口中KEY值对应
	@Getter @Setter private String EventKey;

}
