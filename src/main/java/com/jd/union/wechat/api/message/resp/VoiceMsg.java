package com.jd.union.wechat.api.message.resp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 回复语音消息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class VoiceMsg extends BaseMsg {

	@Getter @Setter private Voice Voice;

}
