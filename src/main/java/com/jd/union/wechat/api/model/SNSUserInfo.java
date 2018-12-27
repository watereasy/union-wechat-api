package com.jd.union.wechat.api.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ClassName: SNSUserInfo
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class SNSUserInfo implements Serializable{

	private static final long serialVersionUID = -525968086315529273L;
	// 用户标识
	@Getter @Setter private String openId;
	// 用户昵称
	@Getter @Setter private String nickname;
	// 性别(1是男性，2是女性，0是未知) 
	@Getter @Setter private int sex;
	// 国家
	@Getter @Setter private String country;
	// 省份
	@Getter @Setter private String province;
	// 城市
	@Getter @Setter private String city;
	// 用户头像链接
	@Getter @Setter private String headImgUrl;
	// 用户特权信息
	@Getter @Setter private List<String> privilegeList;
	// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
	@Getter @Setter private String unionid;

}
