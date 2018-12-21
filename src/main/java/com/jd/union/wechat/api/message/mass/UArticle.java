package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 上传图文消息model
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class UArticle {
	// 图文消息缩略图的media_id，可以在上传多媒体文件接口中获得
	@Getter @Setter private String thumb_media_id;
	// 图文消息的作者
	@Getter @Setter private String author;
	// 图文消息的标题
	@Getter @Setter private String title;
	// 在图文消息页面点击“阅读原文”后的页面
	@Getter @Setter private String content_source_url;
	// 图文消息页面的内容，支持HTML标签
	@Getter @Setter private String content;
	// 图文消息的描述
	@Getter @Setter private String digest;
	// 是否显示封面，1为显示，0为不显示
	@Getter @Setter private String show_cover_pic;
	// 图文页的url
	@Getter @Setter private String url;

}
