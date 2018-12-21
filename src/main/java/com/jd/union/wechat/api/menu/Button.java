package com.jd.union.wechat.api.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 按钮的基类
 * Created by zhangjianhui6 on 2018/12/6.
 *
 **/
@ToString
public class Button {
	/** 菜单标题，不超过16个字节，子菜单不超过40个字节 */
	@Getter @Setter protected String name;

}
