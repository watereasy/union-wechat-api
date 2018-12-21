package com.jd.union.wechat.api.shake.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 创建红包(相应)
 * @author zhangjianhui
 *
 */
public class LotteryAddInfoResp {
	/** 生成的红包活动id */
	@Getter @Setter private String lottery_id;
	/** 生成的模板页面ID */
	@Getter @Setter private int page_id;

}
