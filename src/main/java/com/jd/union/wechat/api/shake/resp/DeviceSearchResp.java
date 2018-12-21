package com.jd.union.wechat.api.shake.resp;

import com.jd.union.wechat.api.shake.Device;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询设备列表(响应)
 * @author zhangjianhui
 *
 */
public class DeviceSearchResp {

	@Getter @Setter private List<Device> devices;

	@Getter @Setter private int total_count;

}
