package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户标签
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class UserTag {

	// 分组id，由微信分配 
	@Getter @Setter private int id;
	// 分组名字，UTF8编码
	@Getter @Setter private String name;
	// 分组内用户数量 
	@Getter @Setter private String count;
	
}
