package com.jd.union.wechat.api.shake.req;

import com.jd.union.wechat.api.shake.DeviceIdentifier;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 配置设备与页面的关联关系(请求)
 * @author zhangjianhui
 *
 */
public class DeviceBindPageReq {

	@Getter @Setter private DeviceIdentifier device_identifier;
	/** 待关联的页面列表  */
	@Getter @Setter private List<Integer> page_ids;

}
