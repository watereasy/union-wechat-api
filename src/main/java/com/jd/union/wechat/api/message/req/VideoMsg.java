package com.jd.union.wechat.api.message.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 视频消息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class VideoMsg extends BaseMsg {
	// 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
	@Getter @Setter private String MediaId;
	// 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	@Getter @Setter private String ThumbMediaId;

}
