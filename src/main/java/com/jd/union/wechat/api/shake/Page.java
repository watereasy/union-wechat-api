package com.jd.union.wechat.api.shake;

import lombok.Getter;
import lombok.Setter;

public class Page {

	@Getter @Setter private int page_id;
	/** 不超过6个汉字或12个英文字母 */
	@Getter @Setter private String title;
	/** 不超过7个汉字或14个英文字母 */
	@Getter @Setter private String description;
	/** 跳转链接 */
	@Getter @Setter private String page_url;
	/** 不超过15个汉字或30个英文字母 */
	@Getter @Setter private String comment;
	/** 在摇一摇页面展示的图片。图片需先上传至微信侧服务器，用“素材管理-上传图片素材”接口上传图片，返回的图片URL再配置在此处 */
	@Getter @Setter private String icon_url;

}
