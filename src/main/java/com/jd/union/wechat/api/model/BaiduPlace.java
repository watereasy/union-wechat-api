package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 地理位置信息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 **/
@ToString
public class BaiduPlace implements Comparable<BaiduPlace>{
	/** 名称 */
	@Getter @Setter private String name;
	/** 地址 */
	@Getter @Setter private String address;
	/** 经度 */
	@Getter @Setter private String lng;
	/** 纬度 */
	@Getter @Setter private String lat;
	/** 电话 */
	@Getter @Setter private String telephone;
	/** 距离 */
	@Getter @Setter private int distance;

	public int compareTo(BaiduPlace baiduPlace) {
		return this.distance - baiduPlace.getDistance();
	}
}
