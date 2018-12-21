package com.jd.union.wechat.api.shake.req;

import com.jd.union.wechat.api.shake.DeviceIdentifier;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询设备列表(请求)
 * @author zhangjianhui
 *
 */
public class DeviceSearchReq {
	/** 查询类型。1：查询设备id列表中的设备；2：分页查询所有设备信息；3：分页查询某次申请的所有设备信息  */
	@Getter @Setter private String type;
	/** 指定的设备 ； 当type为1时，此项为必填  */
	@Getter @Setter private List<DeviceIdentifier> device_identifiers;
	/** 设备列表的起始索引值  */
	@Getter @Setter private int begin;
	/** 待查询的设备数量，不能超过50个 */
	@Getter @Setter private int count;
	/** 批次ID，申请设备ID时所返回的批次ID；当type为3时，此项为必填  */
	@Getter @Setter private int apply_id;

}
