package com.jd.union.wechat.api.message.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图片消息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class ImageMsg extends BaseMsg {
	// 图片链接
	@Getter @Setter private String PicUrl;
	// 图片消息媒体id，可以调用多媒体文件下载接口拉取数据
	@Getter @Setter private String MediaId;

}
