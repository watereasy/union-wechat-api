package com.jd.union.wechat.api.shake;

import lombok.Getter;
import lombok.Setter;

public class DeviceIdentifier {
	/** 设备编号，若填了UUID、major、minor，则可不填设备编号，若二者都填，则以设备编号为优先  */
	@Getter @Setter private int device_id;

	@Getter @Setter private String uuid;

	@Getter @Setter private int major;

	@Getter @Setter private int minor;

}
