package com.jd.union.wechat.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信二维码
 * Created by zhangjianhui6 on 2018/12/6.
 * 
 */
@ToString
public class WeixinQRCode implements Serializable{
	private static final long serialVersionUID = 2466071461527099672L;
	// 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
	@Getter @Setter private String ticket;
	// 二维码的有效时间，以秒为单位。最大不超过2592000（即30天）。
	@Getter @Setter private Integer expireSeconds;
	// 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	@Getter @Setter private String url;

}
