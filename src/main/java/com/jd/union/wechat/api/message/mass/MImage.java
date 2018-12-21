package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 发送类型：图片
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class MImage {
	// AdvancedUtil.uploadMedia 获得
	@Getter @Setter private String media_id;

}
