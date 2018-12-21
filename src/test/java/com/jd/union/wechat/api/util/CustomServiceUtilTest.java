package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.model.CustomAccount;

import common.Constant;
import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.fail;

@Slf4j
public class CustomServiceUtilTest {

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

	/** TODO 客服接口都有问题 调不通 可能没有开通相应权限 */
	@Test
	public void testCreateCustomAccount() {
		CustomAccount ca = new CustomAccount();
		ca.setKf_account("watereasy@rill2mi");
		ca.setNickname("氺易");
		ca.setPassword(MD5Util.getMD5("123456"));
		
		Assert.assertTrue(CustomServiceUtil.createCustomAccount(InitUtil.accessToken, JSONObject.toJSONString(ca)));
	}

	@Test
	public void testUpdateCustomAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCustomAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetCustomAccountAvatar() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllCustomAccount() {
		CustomServiceUtil.getAllCustomAccount(InitUtil.accessToken);
	}

	@Test
	public void testMakeTextCustomMessage() {
		String openId = "oV7Lcjpo037FHR4tMtq973wFze7E";
		String customMsg = CustomServiceUtil.makeTextCustomMessage(openId, "test");
		System.out.println(customMsg);
		CustomServiceUtil.sendCustomMessage(InitUtil.accessToken, customMsg);

	}

	@Test
	public void testMakeImageCustomMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeVoiceCustomMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeVideoCustomMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeMusicCustomMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testMakeNewsCustomMessage() {
		fail("Not yet implemented");
	}

	@Test
	public void testSendCustomMessage() {
		String jsonStr = "{\n" +
				"    \"touser\":\"oV7Lcjpo037FHR4tMtq973wFze7E\",\n" +
				"    \"msgtype\":\"text\",\n" +
				"    \"text\":\n" +
				"    {\n" +
				"         \"content\":\"Hello World, Welcome coosee\"\n" +
				"    }\n" +
				"}";
		System.out.println(CustomServiceUtil.sendCustomMessage(InitUtil.accessToken, jsonStr));
//		fail("Not yet implemented");
	}

}
