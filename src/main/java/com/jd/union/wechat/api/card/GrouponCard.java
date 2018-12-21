package com.jd.union.wechat.api.card;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 团购券
 * @author zhangjianhui
 *
 */
@ToString(callSuper=true)
public class GrouponCard extends BaseCard{
	// 团购券专用，团购详情
	@Getter @Setter private String deal_detail;

}
