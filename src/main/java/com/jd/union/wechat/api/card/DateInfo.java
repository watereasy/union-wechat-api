package com.jd.union.wechat.api.card;

import lombok.Getter;
import lombok.Setter;

/**
 * 卡券日期信息
 * @author zhangjianhui
 *
 */
public class DateInfo {
	// DATE_TYPE_FIX_TIME_RANGE 表示固定日期区间，DATE_TYPE_FIX_TERM表示固定时长(自领取后按天算)
	@Getter @Setter private String type;
	// type为DATE_TYPE_FIX_TIME_RANGE时专用，表示起用时间。从1970年1月1日00:00:00至起用时间的秒数，最终需转换为字符串形态传入。（东八区时间，单位为秒）
	@Getter @Setter private int begin_timestamp;
	// type为DATE_TYPE_FIX_TIME_RANGE时专用，表示结束时间，建议设置为截止日期的23:59:59过期。（东八区时间，单位为秒）
	@Getter @Setter private int end_timestamp;

}
