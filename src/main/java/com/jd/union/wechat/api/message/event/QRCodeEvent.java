package com.jd.union.wechat.api.message.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 扫描带参数二维码事件
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class QRCodeEvent extends BaseEvent {
	// 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	@Getter @Setter private String EventKey;
	// 二维码的ticket，可用来换取二维码图片
	@Getter @Setter private String Ticket;

}
