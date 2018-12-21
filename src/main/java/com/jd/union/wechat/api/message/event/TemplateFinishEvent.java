package com.jd.union.wechat.api.message.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 模板消息发送结果 具体请看"公众平台开发文档=>发送消息=>模板消息接口=>事件推送"
 * TODO 微信会回调，请在业务层自己实现
 * Created by zhangjianhui6 on 2018/12/6.
 * 
 */
@ToString(callSuper = true)
public class TemplateFinishEvent extends BaseEvent {
	// 消息id
	@Getter @Setter private int MsgID;
	// 发送状态：成功/success|拒收/failed:user block|失败/failed: system failed
	@Getter @Setter private String Status;
	
}
