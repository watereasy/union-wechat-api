package com.jd.union.wechat.api.card;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 卡券二维码
 * @author zhangjianhui
 *
 */
@ToString
public class CardQR {
	// 卡券ID
	@Getter @Setter private String card_id;
	// use_custom_code字段为true的卡券必须填写，非自定义code不必填写
	@Getter @Setter private String code;
	// 指定领取者的openid，只有该用户能领取。bind_openid字段为true的卡券必须填写，非指定openid不必填写
	@Getter @Setter private String openid;
	// 指定二维码的有效时间，范围是60 ~ 1800秒。不填默认为永久有效
	@Getter @Setter private Integer expire_seconds;
	// 指定下发二维码，生成的二维码随机分配一个code，领取后不可再次扫描。填写true或false。默认false
	@Getter @Setter private boolean is_unique_code;
	// 领取场景值，用于领取渠道的数据统计，默认值为0，字段类型为整型，长度限制为60位数字。用户领取卡券后触发的事件推送中会带上此自定义场景值
	@Getter @Setter private int outer_id;

}
