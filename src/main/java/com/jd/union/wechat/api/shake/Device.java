package com.jd.union.wechat.api.shake;

import lombok.Getter;
import lombok.Setter;

public class Device {

	@Getter @Setter private int device_id;

	@Getter @Setter private String uuid;

	@Getter @Setter private int major;

	@Getter @Setter private int minor;

	@Getter @Setter private int status;
	/** 设备最近一次被摇到的日期（最早只能获取前一天的数据）；新申请的设备该字段值为0  */
	@Getter @Setter private long last_active_time;

	@Getter @Setter private int poi_id;
	/** 摇周边页面唯一ID  */
	@Getter @Setter private int page_id;

	@Getter @Setter private String common;

}
