package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客服账号
 * Created by zhangjianhui6 on 2018/12/6.
 *
 **/
@ToString
public class CustomAccount {

	@Getter @Setter private String id;
	/** 客服账号 */
	@Getter @Setter private String kf_account;
	/** 用户名 */
	@Getter @Setter private String nickname;
	/** 密码 */
	@Getter @Setter private String password;
	/** 头像 */
	@Getter @Setter private String headimg;

}
