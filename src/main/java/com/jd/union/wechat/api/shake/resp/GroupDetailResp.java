package com.jd.union.wechat.api.shake.resp;

import com.jd.union.wechat.api.shake.Device;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GroupDetailResp {

	@Getter @Setter private int group_id;

	@Getter @Setter private String group_name;

	@Getter @Setter private int total_count;

	@Getter @Setter private List<Device> devices;

}
