package com.jd.union.wechat.api.util;

import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
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

	@Test
	public void testCreateCustomAccount() {
//		CustomAccount ca = new CustomAccount();
////		ca.setKf_account("watereasy@rill2mi");
////		ca.setNickname("氺易");
////		ca.setPassword(MD5Util.getMD5("123456"));

		String kfAccount = "watereasy@rill2mi";
		String nickname = "氺易";
		
		System.out.println(CustomServiceUtil.createCustomAccount(InitUtil.accessToken, kfAccount, nickname));
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

}
