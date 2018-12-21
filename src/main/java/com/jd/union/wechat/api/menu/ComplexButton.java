package com.jd.union.wechat.api.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 复合类型的按钮
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper=true)
public class ComplexButton extends Button {

	@Getter @Setter private Button[] sub_button;

}
