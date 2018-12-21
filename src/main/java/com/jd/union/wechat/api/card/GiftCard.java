package com.jd.union.wechat.api.card;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 礼品券
 * @author zhangjianhui
 *
 */
@ToString(callSuper=true)
public class GiftCard extends BaseCard {
	// 礼品券专用，填写礼品的名称
	@Getter @Setter private String gift;

}
