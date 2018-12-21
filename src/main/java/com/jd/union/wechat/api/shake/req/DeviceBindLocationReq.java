package com.jd.union.wechat.api.shake.req;

import com.jd.union.wechat.api.shake.DeviceIdentifier;

import lombok.Getter;
import lombok.Setter;

/**
 * 编辑设备信息(请求)
 * @author zhangjianhui
 *
 */
public class DeviceBindLocationReq {

	@Getter @Setter private DeviceIdentifier device_identifier;
	/** 设备的备注信息，不超过15个汉字或30个英文字母  */
	@Getter @Setter private String comment;

}
