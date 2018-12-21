package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * OpenID群发视频
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class MPVideoMsg extends MPBaseMsg {

	@Getter @Setter private MVideo mpvideo;

}
