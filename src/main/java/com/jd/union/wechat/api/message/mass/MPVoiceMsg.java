package com.jd.union.wechat.api.message.mass;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * OpenID发送语音
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class MPVoiceMsg extends MPBaseMsg {

	@Getter @Setter private MVoice voice;

}
