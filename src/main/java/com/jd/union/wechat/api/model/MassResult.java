package com.jd.union.wechat.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 群发结果
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class MassResult implements Serializable{
	private static final long serialVersionUID = -2296409239680185199L;
	// 消息发送任务的ID
	@Getter @Setter private Integer msgId;
	// 消息发送后的状态，SEND_SUCCESS表示发送成功，SENDING表示发送中，SEND_FAIL表示发送失败，DELETE表示已删除
	@Getter @Setter private String msgStatus;
	// 消息的数据ID，该字段只有在群发图文消息时，才会出现。可以用于在图文分析数据接口中，获取到对应的图文消息的数据，是图文分析数据接口中的msgid字段中的前半部分，详见图文分析数据接口中的msgid字段的介绍。
	@Getter @Setter private Long msgDataId;

}
