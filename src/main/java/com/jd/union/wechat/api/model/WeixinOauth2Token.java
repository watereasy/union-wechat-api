package com.jd.union.wechat.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 网页授权access_token
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class WeixinOauth2Token implements Serializable{

	private static final long serialVersionUID = 6442006327338232282L;
	// 网页授权接口调用凭证
	@Getter @Setter private String accessToken;
	// 凭证有效时长(s)
	@Getter @Setter private int expiresIn;
	// 用于刷新凭证
	@Getter @Setter private String refreshToken;
	// 用户标识
	@Getter @Setter private String openId;
	// 用户授权作用域
	@Getter @Setter private String scope;

}
