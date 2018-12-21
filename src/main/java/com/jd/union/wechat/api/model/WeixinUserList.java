package com.jd.union.wechat.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 公众号关注用户列表
 *
 */
@ToString
public class WeixinUserList {
	// 关注该公众账号的总用户数
	@Getter @Setter private long total;
	// 拉取的OPENID个数，最大值为10000 
	@Getter @Setter private int count;
	// 列表数据，OPENID的列表 
	@Getter @Setter private List<String> openIdList;
	// 拉取列表的后一个用户的OPENID 
	@Getter @Setter private String nextOpenId;

}
