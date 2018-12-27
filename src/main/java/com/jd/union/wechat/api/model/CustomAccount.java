package com.jd.union.wechat.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 客服账号
 * Created by zhangjianhui6 on 2018/12/6.
 *
 **/
@ToString
public class CustomAccount implements Serializable{

	private static final long serialVersionUID = -2167213474234834461L;
	/** 客服编号 */
	@Getter @Setter private String kf_id;
	/** 完整客服帐号，格式为：帐号前缀@公众号微信号 */
	@Getter @Setter private String kf_account;
	/** 客服头像 */
	@Getter @Setter private String kf_headimgurl;
	/** 客服昵称 */
	@Getter @Setter private String kf_nick;
	/** 如果客服帐号已绑定了客服人员微信号， 则此处显示微信号 */
	@Getter @Setter private String kf_wx;
	/** 如如果客服帐号尚未绑定微信号，但是已经发起了一个绑定邀请， 则此处显示绑定邀请的微信号 */
	@Getter @Setter private String invite_wx;
	/** 如果客服帐号尚未绑定微信号，但是已经发起过一个绑定邀请， 邀请的过期时间，为unix 时间戳 */
	@Getter @Setter private Long invite_expire_time;
	/** 邀请的状态，有等待确认“waiting”，被拒绝“rejected”， 过期“expired” */
	@Getter @Setter private String invite_status;

}
