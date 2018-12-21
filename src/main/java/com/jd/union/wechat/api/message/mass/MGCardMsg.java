package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分组发送卡券
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class MGCardMsg extends MGBaseMsg {

	@Getter @Setter private MCard wxcard;

}
