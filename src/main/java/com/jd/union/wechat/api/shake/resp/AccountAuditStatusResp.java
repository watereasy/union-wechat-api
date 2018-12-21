package com.jd.union.wechat.api.shake.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询审核状态(响应)
 * @author zhangjianhui
 *
 */
public class AccountAuditStatusResp {
	/** 提交申请的时间戳 */
	@Getter @Setter private long apply_time;
	/** 审核备注，包括审核不通过的原因  */
	@Getter @Setter private String audit_comment;
	/** 审核状态。0：审核未通过、1：审核中、2：审核已通过；审核会在三个工作日内完成  */
	@Getter @Setter private int audit_status;
	/** 确定审核结果的时间戳；若状态为审核中，则该时间值为0 */
	@Getter @Setter private long audit_time;

}
