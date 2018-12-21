package com.jd.union.wechat.api.message.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 地理位置消息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class LocationMsg extends BaseMsg {
	// 地理位置维度X
	@Getter @Setter private String Location_X ;
	// 地理位置维度Y
	@Getter @Setter private String Location_Y;
	// 地图缩放大小
	@Getter @Setter private String Scale;
	// 地理位置信息
	@Getter @Setter private String Label;

}
