package com.jd.union.wechat.api.message.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 视频model
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class Video {
	// 通过上传多媒体文件，得到的id
	@Getter @Setter private String MediaId;
	// 视频消息的标题
	@Getter @Setter private String Title;
	// 视频消息的描述
	@Getter @Setter private String Description;

}
