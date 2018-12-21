package com.jd.union.wechat.api.shake.req;

import com.jd.union.wechat.api.shake.DeviceIdentifier;

import lombok.Getter;
import lombok.Setter;

/**
 * 以设备为维度的数据统计接口(请求)
 * @author zhangjianhui
 *
 */
public class StatisticsDeviceReq {

	@Getter @Setter private DeviceIdentifier device_identifier;

	@Getter @Setter private long begin_date;

	@Getter @Setter private long end_date;

}
