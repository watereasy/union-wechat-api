package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * jsapi_ticket的model
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class JSapiTicket {

	@Getter @Setter private String errCode;

	@Getter @Setter private String errMsg;

	@Getter @Setter private String ticket;

	@Getter @Setter private int expiresIn;

}
