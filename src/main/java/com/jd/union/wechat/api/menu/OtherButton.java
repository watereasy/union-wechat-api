package com.jd.union.wechat.api.menu;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 其他类型按钮
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper=true)
public class OtherButton extends Button{
	/** scancode_waitmsg:扫码带提示|scancode_push:扫码推事件|pic_sysphoto:系统拍照发图
	// pic_photo_or_album:拍照或者相册发图|pic_weixin:微信相册发图|location_select:地理位置选择
	// 仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户 */
	@Getter @Setter private String type;

	@Getter @Setter private String key;

	@Getter @Setter private List<Object> sub_button;

}
