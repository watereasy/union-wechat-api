package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * OpenID群发图文
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class MPNewsMsg extends MPBaseMsg{

	@Getter @Setter private MNews mpnews;
	/** 图文消息被判定为转载时，是否继续群发。 1为继续群发（转载），0为停止群发。 该参数默认为0。 */
	@Getter @Setter private Integer send_ignore_reprint;

}
