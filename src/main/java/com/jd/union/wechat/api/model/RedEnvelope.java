package com.jd.union.wechat.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 红包
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class RedEnvelope {
	/** 随机字符串 */
	@Getter @Setter private String nonce_str;
	/** 签名 */
	@Getter @Setter private String sign;
	/** 商户订单号 */
	@Getter @Setter private String mch_billno;
	/** 商户号 */
	@Getter @Setter private String mch_id;
	/** 公众账号appid */
	@Getter @Setter private String wxappid;
	/** 商户名称 */
	@Getter @Setter private String send_name;
	/** 用户openid */
	@Getter @Setter private String re_openid;
	/** 付款金额(分) */
	@Getter @Setter private Integer total_amount;
	/** 红包发放总人数 */
	@Getter @Setter private Integer total_num;
	/** (裂变)红包金额设置方式  ALL_RAND—全部随机*/
	@Getter @Setter private String amt_type;
	/** 红包祝福语 */
	@Getter @Setter private String wishing;
	/** Ip地址 */
	@Getter @Setter private String client_ip;
	/** 活动名称 */
	@Getter @Setter private String act_name;
	/** 备注 */
	@Getter @Setter private String remark;
	/** 场景id */
	@Getter @Setter private String scene_id;
	/** 活动信息 */
	@Getter @Setter private String risk_info;
	/** 资金授权商户号 */
	@Getter @Setter private String consume_mch_id;

}
