package com.jd.union.wechat.api.message.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图片model
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class Image {
	// 通过上传多媒体文件，得到的id
	@Getter @Setter private String MediaId;

}
