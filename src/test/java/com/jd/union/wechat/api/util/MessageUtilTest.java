package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSON;

import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.fail;

@Slf4j
public class MessageUtilTest {

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
	public void testMakeTextCustomMessage() {
		String openId = "oV7Lcjpo037FHR4tMtq973wFze7E";
		String customMsg = CustomServiceUtil.makeTextCustomMessage(openId, "test");
		System.out.println(customMsg);

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
		System.out.println(JSON.toJSONString(MessageUtil.sendCustomMessage(InitUtil.accessToken, jsonStr)));
	}

}
