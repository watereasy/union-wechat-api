package com.jd.union.wechat.api.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * click类型的按钮
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper=true)
public class ClickButton extends Button {
	// click:点击
	@Getter @Setter private String type;

	@Getter @Setter private String key;

}
