package com.jd.union.wechat.api.message.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上报地理位置事件
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class LocationEvent extends BaseEvent {
	// 地理位置纬度
	@Getter @Setter private String Latitude;
	// 地理位置经度
	@Getter @Setter private String Longitude;
	// 地理位置精度
	@Getter @Setter private String Precision;

}
