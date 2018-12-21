package com.jd.union.wechat.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户标签中的成员
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class UserTagMember {
	
	public UserTagMember() {
		super();
	}

	public UserTagMember(int tagid, List<String> openid_list) {
		super();
		this.tagid = tagid;
		this.openid_list = openid_list;
	}

	/** 标签ID */
	@Getter @Setter private int tagid;
	/** 粉丝列表 */
	@Getter @Setter private List<String> openid_list;

}
