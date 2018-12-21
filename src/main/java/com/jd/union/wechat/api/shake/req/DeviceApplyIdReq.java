package com.jd.union.wechat.api.shake.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 申请设备ID(请求)
 * @author zhangjianhui
 *
 */
public class DeviceApplyIdReq {
	/** 申请的设备ID的数量，单次新增设备超过500个，需走人工审核流程 */
	@Getter @Setter private int quantity;
	/** 申请理由，不超过100个汉字或200个英文字母  */
	@Getter @Setter private String apply_reason;
	/** 备注，不超过15个汉字或30个英文字母 */
	@Getter @Setter private String comment;
	/** 设备关联的门店ID，关联门店后，在门店1KM的范围内有优先摇出信息的机会 */
	@Getter @Setter private int poi_id;

}
