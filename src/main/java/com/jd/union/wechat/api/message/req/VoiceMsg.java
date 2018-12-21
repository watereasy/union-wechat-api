package com.jd.union.wechat.api.message.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 语音消息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class VoiceMsg extends BaseMsg {
	// 语音消息媒体id，可以调用多媒体文件下载接口拉取数据
	@Getter @Setter private String MediaId;
	// 语音格式，如amr，speex等
	@Getter @Setter private String Format;
	// 语音amr识别结果 ，UTF8编码
	@Getter @Setter private String Recognition;

}
