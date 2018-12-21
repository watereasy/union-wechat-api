package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 微信二维码
 * Created by zhangjianhui6 on 2018/12/6.
 * 
 */
@ToString
public class WeixinQRCode {
	// 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
	@Getter @Setter private String ticket;
	// 二维码的有效时间，以秒为单位。最大不超过1800。
	@Getter @Setter private int expireSeconds;
	// 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	@Getter @Setter private String url;

}
