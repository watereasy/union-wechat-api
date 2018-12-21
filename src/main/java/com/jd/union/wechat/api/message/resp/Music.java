package com.jd.union.wechat.api.message.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 音乐model
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class Music {
	// 音乐标题 
	@Getter @Setter private String Title;
	// 音乐描述
	@Getter @Setter private String Description;
	// 音乐链接
	@Getter @Setter private String MusicUrl;
	// 高质量音乐链接，WIFI环境优先使用该链接播放音乐 
	@Getter @Setter private String HQMusicUrl;
	// 缩略图的媒体id，通过上传多媒体文件，得到的id
	@Getter @Setter private String ThumbMediaId;

}
