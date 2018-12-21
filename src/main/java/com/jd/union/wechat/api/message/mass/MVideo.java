package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 发送类型：视频
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class MVideo {
	// AdvancedUtil.getMassVideo 获得
	@Getter @Setter private String media_id;

}
