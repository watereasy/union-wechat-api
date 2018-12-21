package com.jd.union.wechat.api.shake;

import lombok.Getter;
import lombok.Setter;

/**
 * 摇一摇数据统计
 * @author zhangjianhui
 *
 */
public class Statistics {
	/** 点击摇周边消息的次数 */
	@Getter @Setter private long click_pv;
	/** 点击摇周边消息的人数  */
	@Getter @Setter private long click_uv;
	/** 当天0点对应的时间戳  */
	@Getter @Setter private long ftime;
	/** 摇周边的次数  */
	@Getter @Setter private long shake_pv;
	/** 摇周边的人数   */
	@Getter @Setter private long shake_uv;

}
