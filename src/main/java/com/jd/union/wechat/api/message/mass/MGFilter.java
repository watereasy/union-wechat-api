package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用于设定图文消息的接收者 
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class MGFilter {
	// 用于设定是否向全部用户发送,true:所有/false:指定group_id
	@Getter @Setter private boolean is_to_all;
	// 群发到的分组的group_id,若is_to_all值为true，可不填写group_id
	@Getter @Setter private String group_id;

}
