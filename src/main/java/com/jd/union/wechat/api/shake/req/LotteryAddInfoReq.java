package com.jd.union.wechat.api.shake.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 摇一摇 创建红包(请求)
 * @author zhangjianhui
 *
 */
public class LotteryAddInfoReq {
	/** 抽奖活动名称（选择使用模板时，也作为摇一摇消息主标题），最长6个汉字，12个英文字母  */
	@Getter @Setter private String title;
	/** 抽奖活动描述（选择使用模板时，也作为摇一摇消息副标题），最长7个汉字，14个英文字母 */
	@Getter @Setter private String desc;
	/** 抽奖开关。0关闭，1开启，默认为1 */
	@Getter @Setter private int onoff;
	/** 抽奖活动开始时间，unix时间戳，单位秒  */
	@Getter @Setter private long begin_time;
	/** 抽奖活动结束时间，unix时间戳，单位秒,红包活动有效期最长为91天  */
	@Getter @Setter private long expire_time;
	/** 红包提供商户公众号的appid，需与预下单中的公众账号appid（wxappid）一致  */
	@Getter @Setter private String sponsor_appid;
	/** 红包总数，红包总数是录入红包ticket总数的上限，因此红包总数应该大于等于预下单时红包ticket总数  */
	@Getter @Setter private int total;
	/** 红包关注界面后可以跳转到第三方自定义的页面  */
	@Getter @Setter private String jump_url;
	/** 开发者自定义的key，用来生成活动抽奖接口的签名参数，长度32位。使用方式见sign生成规则  */
	@Getter @Setter private String key;

}
