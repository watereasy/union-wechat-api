package com.jd.union.wechat.api.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * view类型的按钮
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper=true)
public class ViewButton extends Button{
	// view:跳转URL
	@Getter @Setter private String type;
	// 必须有效
	@Getter @Setter private String url;

}
