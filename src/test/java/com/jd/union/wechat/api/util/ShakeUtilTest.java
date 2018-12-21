package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.shake.BeaconInfo;
import com.jd.union.wechat.api.shake.req.AccountRegisterReq;
import com.jd.union.wechat.api.shake.resp.UserGetShakeInfoResp;

import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShakeUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitUtil.initAccessToken();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegister() {
		AccountRegisterReq register = new AccountRegisterReq();
		register.setName("zhang_san");
		register.setPhone_number("13512345678");
		register.setEmail("weixin123@qq.com");
		register.setIndustry_id("0118");
		List<String> qualification_cert_urls = new ArrayList<>();
		qualification_cert_urls.add("http://shp.qpic.cn/wx_shake_bus/0/1428565236d03d864b7f43db9ce34df5f720509d0e/0");
		qualification_cert_urls.add("http://shp.qpic.cn/wx_shake_bus/0/1428565236d03d864b7f43db9ce34df5f720509d0e/0");
		register.setQualification_cert_urls(qualification_cert_urls);
		register.setApply_reason("test");
		String jsonMsg = JSONObject.toJSONString(register);
		String data = ShakeUtil.accountRegister(InitUtil.accessToken, jsonMsg);
		System.out.println(data);
	}
	
	@Test
	public void test01(){
		// JSON对象和复杂的对象(内嵌对象<一层>)相互转换
		Map<String, Object> map = new HashMap<>();
		map.put("page_id", 14211);
		
		BeaconInfo beacon = new BeaconInfo();
		beacon.setDistance(55.00620700469034);
		beacon.setMajor(10001);
		beacon.setMinor(19007);
		beacon.setUuid("FDA50693-A4E2-4FB1-AFCF-C6EB07647825");
		map.put("beacon_info", beacon);
		map.put("openid", "oVDmXjp7y8aG2AlBuRpMZTb1-cmA");
		map.put("poi_id", 1234);
		JSONObject jsonObj = JSONObject.parseObject(JSON.toJSONString(map));
		
		UserGetShakeInfoResp shakeInfo = JSONObject.parseObject(jsonObj.toJSONString(), UserGetShakeInfoResp.class);
		
		System.out.println(shakeInfo.getOpenid() + "|" + shakeInfo.getPage_id() + "|" + shakeInfo.getPoi_id() );
		System.out.println(shakeInfo.getBeacon_info().getDistance() + "|" + shakeInfo.getBeacon_info().getMajor() + "|" + shakeInfo.getBeacon_info().getMinor() + "|" + shakeInfo.getBeacon_info().getUuid());
		// JSON对象和复杂的对象(内嵌对象<一层>)相互转换 => 应该可以更复杂的对象相互转换
	}

}
