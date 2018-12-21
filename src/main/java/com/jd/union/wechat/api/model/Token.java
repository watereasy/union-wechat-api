package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 基础access_token
 * Created by zhangjianhui6 on 2018/12/6.
 */
@ToString
public class Token {

	// 接口访问凭证
	@Getter @Setter String accessToken;
	// 凭证有效期，单位：秒
	@Getter @Setter private int expiresIn;

}
