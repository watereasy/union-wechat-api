package com.jd.union.wechat.api.shake;

import lombok.Getter;
import lombok.Setter;

/**
 * 设备分组
 * @author zhangjianhui
 *
 */
public class Group {

	@Getter @Setter private int group_id;
	/** 分组名称，不超过100汉字或200个英文字母  */
	@Getter @Setter private String group_name;

}
