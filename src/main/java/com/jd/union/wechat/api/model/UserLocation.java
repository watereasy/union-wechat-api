package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户地理位置model
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class UserLocation {

	@Getter @Setter private String openId;

	@Getter @Setter private String lng;

	@Getter @Setter private String lat;

	@Getter @Setter private String bd09Lng;

	@Getter @Setter private String bd09Lat;

}
