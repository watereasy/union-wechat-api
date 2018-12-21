package com.jd.union.wechat.api.shake.req;

import com.alibaba.fastjson.JSONArray;

import lombok.Getter;
import lombok.Setter;

/**
 * 录入红包（请求）
 * @author zhangjianhui
 *
 */
public class LotterySetPrizeReq {

	@Getter @Setter private String lottery_id;

	@Getter @Setter private String mchid;

	@Getter @Setter private String sponsor_appid;

	@Getter @Setter private JSONArray prize_info_list ;
	
}
