package com.jd.union.wechat.api.util;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;

public class RedEnvelopeUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSendComm() throws Exception{
		String password = "1382588802";
		String certFile = "D:/Program Files/Java/jdk1.6.0_45/lib/security/apiclient_cert.p12";
		Map<String, Object> params = new HashMap<String, Object>();
		String mch_id = "1382588802";
		params.put("nonce_str", CommonUtil.random(30));
		params.put("mch_id", mch_id);
		params.put("mch_billno", mch_id + String.valueOf(System.currentTimeMillis()) + CommonUtil.random(4));
		params.put("wxappid", "wx63457c110481fdf2");
		params.put("send_name", "天龙8部");
		params.put("re_openid", "oR8rRvhSBAkn07aeeEmtTAg_ob8w");
		params.put("total_amount", 100);
		params.put("total_num", 1);
		params.put("wishing", "大SASA赛高");
		params.put("client_ip", "111.206.12.22");
		params.put("act_name", "TesT活动22");
		params.put("remark", "test的remark");
		params.put("scene_id", null);
		params.put("risk_info", null);
		params.put("consume_mch_id", null);
		params.put("apikey", "tlactivitychangyoucomwxapikey487");
		String msg = RedEnvelopeUtil.sendComm(password, certFile, params);
		System.out.println(msg);
	}
	
	@Test
	public void testSendComm2() throws Exception{
		String password = "1382588802";
		String certFile = "D:/Program Files/Java/jdk1.6.0_45/lib/security/apiclient_cert.p12";
		Map<String, Object> params = new HashMap<String, Object>();
		String mch_id = "1382588802";
		params.put("nonce_str", CommonUtil.random(30));
		params.put("mch_id", mch_id);
		params.put("mch_billno", mch_id + String.valueOf(System.currentTimeMillis()) + CommonUtil.random(4));
		params.put("wxappid", "wx63457c110481fdf2");
		params.put("send_name", "天龙8部");
		params.put("re_openid", "oR8rRvhSBAkn07aeeEmtTAg_ob8w");
		params.put("total_amount", 500);
		params.put("total_num", 4);
		params.put("wishing", "大SASA赛高");
		params.put("client_ip", "111.206.12.22");
		params.put("act_name", "TesT活动22");
		params.put("remark", "test的remark");
		params.put("scene_id", null);
		params.put("risk_info", null);
		params.put("consume_mch_id", null);
		params.put("apikey", "tlactivitychangyoucomwxapikey487");
		params.put("amt_type", "ALL_RAND");
		String msg = RedEnvelopeUtil.sendComm(password, certFile, params);
		//System.out.println(msg);
	}

}
