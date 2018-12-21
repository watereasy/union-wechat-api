package com.jd.union.wechat.api.card;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 折扣券
 * @author zhangjianhui
 *
 */
@ToString(callSuper=true)
public class DiscountCard extends BaseCard {
	// 折扣券专用，表示打折额度（百分比）。填30就是七折
	@Getter @Setter private int discount;

}
