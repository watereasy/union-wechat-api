package com.jd.union.wechat.api.util;

import com.jd.union.wechat.api.model.Token;

import common.CommUtil;
import common.Constant;
import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CommonUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		InitUtil.initAccessToken();
	}

	@AfterClass
	public static void tearDownAfterClass() {

	}

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testGetToken() throws Exception{
		long createTime = Long.valueOf(CommUtil.getProperties(Constant.CREATE_TIME));
		if(System.currentTimeMillis() - createTime > 7000*1000){
			Token token = CommonUtil.getToken(Constant.APPID, Constant.APPSECRET);
			CommUtil.setProperties("accessToken", token.getAccessToken());
			CommUtil.setProperties("createTime", String.valueOf(System.currentTimeMillis()));
		}
		
		System.out.println("tokenAccess:" + CommUtil.getProperties(Constant.ACCESS_TOKEN));
		System.out.println("createTime:" + CommUtil.getProperties(Constant.CREATE_TIME));
		//tokenAccess = token.getAccessToken();
	}
	
	@Test
	public void testGetWXIpList(){
		List<String> ipList = CommonUtil.getWXIpList(InitUtil.accessToken);
		System.out.println("微信IP列表：" + ipList.toString());
	}

}
