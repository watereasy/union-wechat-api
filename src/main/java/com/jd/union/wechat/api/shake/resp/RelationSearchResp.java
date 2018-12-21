package com.jd.union.wechat.api.shake.resp;

import com.jd.union.wechat.api.shake.Device;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询设备与页面的关联关系(响应)
 * @author zhangjianhui
 *
 */
public class RelationSearchResp {

	@Getter @Setter private List<Device> devices;

	@Getter @Setter private int total_count;

}
