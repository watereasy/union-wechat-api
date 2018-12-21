package com.jd.union.wechat.api.shake.req;

import com.jd.union.wechat.api.shake.DeviceIdentifier;

import lombok.Getter;
import lombok.Setter;

/**
 * 配置设备与门店的关联关系(请求)
 * @author zhangjianhui
 *
 */
public class DeviceApplyUpdateReq {

	@Getter @Setter private DeviceIdentifier device_identifier;

	@Getter @Setter private int poi_id;

}
