package com.jd.union.wechat.api.card;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 一般优惠券
 * @author zhangjianhui
 *
 */
@ToString(callSuper=true)
public class GeneralCouponCard extends BaseCard {
	// 优惠券专用，填写优惠详情
	@Getter @Setter private String default_detail;

}
