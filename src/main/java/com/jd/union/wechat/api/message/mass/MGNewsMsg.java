package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分组群发图文
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class MGNewsMsg extends MGBaseMsg{

	@Getter @Setter private MNews mpnews;

}
