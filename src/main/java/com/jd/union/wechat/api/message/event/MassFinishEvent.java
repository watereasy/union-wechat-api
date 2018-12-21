package com.jd.union.wechat.api.message.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 事件推送群发结果 具体请看"公众平台开发文档=>发送消息=>高级群发接口=>事件推送群发结果"
 * TODO 微信会回调，请在业务层自己实现
 * Created by zhangjianhui6 on 2018/12/6.
 * 
 */
@ToString(callSuper = true)
public class MassFinishEvent extends BaseEvent {
	// 群发的消息ID 
	@Getter @Setter private int MsgID;
	// 群发的结果
	@Getter @Setter private String Status;
	// group_id下粉丝数；或者openid_list中的粉丝数 
	@Getter @Setter private int TotalCount;
	// 过滤
	@Getter @Setter private int FilterCount;
	// 发送成功的粉丝数
	@Getter @Setter private int SentCount;
	// 发送失败的粉丝数 
	@Getter @Setter private int ErrorCount;

}
