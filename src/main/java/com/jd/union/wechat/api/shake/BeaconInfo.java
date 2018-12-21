package com.jd.union.wechat.api.shake;

import lombok.Getter;
import lombok.Setter;

/**
 * 设备信息
 * @author zhangjianhui
 *
 */
public class BeaconInfo {
	/** Beacon信号与手机的距离，单位为米  */
	@Getter @Setter private double distance;

	@Getter @Setter private String uuid;

	@Getter @Setter private int major;

	@Getter @Setter private int minor;

}
