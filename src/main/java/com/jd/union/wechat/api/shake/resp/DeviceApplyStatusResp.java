package com.jd.union.wechat.api.shake.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询设备ID申请审核状态(响应)
 * @author zhangjianhui
 *
 */
public class DeviceApplyStatusResp {
	/** */
	@Getter @Setter private long apply_time;
	/** */
	@Getter @Setter private String audit_comment;
	/** 审核状态。0：审核未通过、1：审核中、2：审核已通过 */
	@Getter @Setter private int audit_status;
	/** 确定审核结果的时间戳，若状态为审核中，则该时间值为0  */
	@Getter @Setter private long audit_time;

}
