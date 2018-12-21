package com.jd.union.wechat.api.shake.req;

import com.jd.union.wechat.api.shake.DeviceIdentifier;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询设备与页面的关联关系(请求)
 * 
 * @author zhangjianhui
 * 
 */
public class RelationSearchReq {
	/** 查询方式。1： 查询设备的关联关系；2：查询页面的关联关系 */
	@Getter @Setter private String type;
	/** 指定的设备 ； 当type为1时，此项为必填 */
	@Getter @Setter private List<DeviceIdentifier> device_identifiers;
	/** 关联关系列表的起始索引值；当type为2时，此项为必填 */
	@Getter @Setter private int begin;
	/** 待查询的关联关系数量，不能超过50个；当type为2时，此项为必填 */
	@Getter @Setter private int count;
	/** 指定的页面id；当type为2时，此项为必填 */
	@Getter @Setter private int page_id;

}
