package com.jd.union.wechat.api.card;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 代金券
 * 
 * @author zhangjianhui
 * 
 */
@ToString(callSuper=true)
public class CashCard extends BaseCard {
	// 代金券专用，表示起用金额。（单位为分）
	@Getter @Setter private int least_cost;
	// 代金券专用，表示减免金额。（单位为分）
	@Getter @Setter private int reduce_cost;

}
