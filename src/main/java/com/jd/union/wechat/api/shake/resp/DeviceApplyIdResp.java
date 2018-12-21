package com.jd.union.wechat.api.shake.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 申请设备ID(响应)
 * @author zhangjianhui
 *
 */
public class DeviceApplyIdResp {
	/** 申请的批次ID，可用在“查询设备列表”接口按批次查询本次申请成功的设备ID */
	@Getter @Setter private int apply_id;
	/** 审核状态。0：审核未通过、1：审核中、2：审核已通过 */
	@Getter @Setter private int audit_status;
	/** 审核备注 */
	@Getter @Setter private String audit_comment;

}
