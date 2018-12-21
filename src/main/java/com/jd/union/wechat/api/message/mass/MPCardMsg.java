package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * OpenID发送卡券
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class MPCardMsg extends MPBaseMsg {

	@Getter @Setter private MCard wxcard;

}
