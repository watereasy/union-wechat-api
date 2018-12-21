package com.jd.union.wechat.api.message.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图文model
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class Article {
	// 图文消息标题
	@Getter @Setter private String Title;
	// 图文消息描述
	@Getter @Setter private String Description;
	// 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	@Getter @Setter private String PicUrl;
	// 点击图文消息跳转链接
	@Getter @Setter private String Url;

}
