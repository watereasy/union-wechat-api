package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上传多媒体文件
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class WeixinMedia {
	// 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
	@Getter @Setter private String type;
	// 媒体文件上传后，获取时的唯一标识 
	@Getter @Setter private String mediaId;
	// 媒体文件上传时间戳 
	@Getter @Setter private long createdAt;
	// 新增的图片素材的图片URL（仅新增图片素材时会返回该字段）
	@Getter @Setter private String url;

}
