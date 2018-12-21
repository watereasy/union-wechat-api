package com.jd.union.wechat.api.shake.req;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 摇一摇申请开通(请求)
 * @author zhangjianhui
 *
 */
public class AccountRegisterReq {
	/** 联系人姓名，不超过20汉字或40个英文字母  */
	@Getter @Setter private String name;

	@Getter @Setter private String phone_number;

	@Getter @Setter private String email;

	@Getter @Setter private String industry_id;

	@Getter @Setter private List<String> qualification_cert_urls;
	/** 申请理由，不超过250汉字或500个英文字母  */
	@Getter @Setter private String apply_reason;

}
