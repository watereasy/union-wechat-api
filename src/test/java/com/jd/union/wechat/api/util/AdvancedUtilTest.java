package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.message.mass.MGFilter;
import com.jd.union.wechat.api.message.mass.MGNewsMsg;
import com.jd.union.wechat.api.message.mass.MNews;
import com.jd.union.wechat.api.message.mass.MPNewsMsg;
import com.jd.union.wechat.api.message.mass.MPVideoMsg;
import com.jd.union.wechat.api.message.mass.MVideo;
import com.jd.union.wechat.api.model.SNSUserInfo;
import com.jd.union.wechat.api.model.WeixinOauth2Token;
import com.jd.union.wechat.api.model.WeixinQRCode;

import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.fail;

@Slf4j
public class AdvancedUtilTest {

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
	public void testGetOauth2AccessToken() {
		String appId = "";
		String appSecret = "";
		String code = "";
		Response<WeixinOauth2Token> response = AdvancedUtil.getOauth2AccessToken(appId, appSecret, code);
		System.out.println("testGetOauth2AccessToken:" + JSON.toJSONString(response));
	}

	@Test
	public void testRefreshOauth2AccessToken() {
		String appId = "";
		String refreshToken  = "";
		Response<WeixinOauth2Token> response = AdvancedUtil.refreshOauth2AccessToken(appId, refreshToken);
		System.out.println("testRefreshOauth2AccessToken:" + JSON.toJSONString(response));
	}

	@Test
	public void testCheckOauth2AccessToken() {
		String accessToken = "";
		String openId = "";
		Response response = AdvancedUtil.checkOauth2AccessToken(accessToken, openId);
		System.out.println("testCheckOauth2AccessToken:" + JSON.toJSONString(response));
	}

	@Test
	public void testGetSNSUserInfo() {
		//fail("Not yet implemented");
		/*String accessToken = "EH7kU5-UUXB3NwE6MbHR6mb-Fyen6QGBjRvMLmy0jUdjHhK1RmFdEReXuTgX7A1wir-4mdDSxxnUmln5m2aZxox-vqjuavHyhAl7l4SeNg8";
		System.out.println(InitUtil.accessToken);
		accessToken = InitUtil.accessToken;
		SNSUserInfo user = AdvancedUtil.getSNSUserInfo(accessToken, "oNI8hs5zE2DjCN58Mdpiw8xkQYks");*/
		Response<SNSUserInfo> user = AdvancedUtil.getSNSUserInfo(InitUtil.accessToken, "oR8rRvhSBAkn07aeeEmtTAg_ob8w");
		System.out.println(JSONObject.toJSONString(user));
	}

	@Test
	public void testCreateTemporaryQRCode() {
		Response<WeixinQRCode> user = AdvancedUtil.createTemporaryQRCode(InitUtil.accessToken, 600, 1);
		String ticket = user.getResult().getTicket();
		//int expireSecond = wqr.getExpireSeconds();
		System.out.println(AdvancedUtil.getQRCode(ticket, "D:/Download"));
	}

	@Test
	public void testCreatePermanentQRCode() {
		Response<WeixinQRCode> response = AdvancedUtil.createPermanentQRCode(InitUtil.accessToken, 1);
		System.out.println("testCreatePermanentQRCode,response:{}" + JSON.toJSONString(response));
		System.out.println(AdvancedUtil.getQRCode(response.getResult().getTicket(), "D:/Download"));
	}

	@Test
	public void testGetQRCode() {
		String ticked = "XXXXXXXXXXXXXXXxx";
		System.out.println(AdvancedUtil.getQRCode(ticked, "D:/Download"));
	}

	@Test
	public void testLongUrl2shortUrl() {
		String longUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx63457c110481fdf2&redirect_uri=http%3a%2f%2ftlactivity.changyou.com%2ftl%2ftwCombine%2fauth.ncdo%3ffrom%3dQR&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		Response<String> response = AdvancedUtil.longUrl2shortUrl(InitUtil.accessToken, longUrl);
		System.out.println("testLongUrl2shortUrl:" + response.getResult());
		// http://w.url.cn/s/Ae8hx2s
	}

	@Test
	public void testSetIndustry() {
		// TODO 未实际测试
		// 行业代码见：http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html -> 行业代码查询
		AdvancedUtil.setIndustry(InitUtil.accessToken, "1", "4");
	}
	
	@Test
	public void testGetIndustry() {
		JSONObject jsonObj = AdvancedUtil.getIndustry(InitUtil.accessToken);
		System.out.println(jsonObj.toString());
		/*{"primary_industry":{"first_class":"IT科技","second_class":"网络游戏"},
		   "secondary_industry":{"first_class":"文体娱乐","second_class":"文化|传媒"}} */

	}

	@Test
	public void testAddTemplateMsg() {
		Response<String> response = AdvancedUtil.addTemplateMsg(InitUtil.accessToken, "TM00154");
		System.out.println(JSON.toJSONString(response));
	}
	
	@Test
	public void testGetAllTempalte() {
		JSONObject jsonObj = AdvancedUtil.getAllTempalte(InitUtil.accessToken);
		System.out.println(jsonObj.toString());
	}
	
	@Test
	public void testDeleteTempalte() {
		Response response = AdvancedUtil.deleteTemplate(InitUtil.accessToken, "MemTJRsAgxGbULTnctj9QAQ0USUM7t5tLJBeLLTg0X4");
		System.out.println(JSON.toJSONString(response));
	}

	@Test
	public void testSendTemplateMsg() {
		Map<String, Object> wrapMap = new LinkedHashMap<String, Object>();
		wrapMap.put("touser", "oNI8hs5zE2DjCN58Mdpiw8xkQYks"); // openId
		wrapMap.put("template_id", "ejz6euaCT-hOVhwwDdyHgY_pJIkJdztbTlohwRv46UU"); // templateId
		wrapMap.put("url", "http://weixin.qq.com/download");
		wrapMap.put("topcolor", "#FF0000");
		
		HashMap<String, Object> dataMap = new LinkedHashMap<String, Object>();
		Map<String, String> firstMap = new LinkedHashMap<String, String>();
		firstMap.put("value", "您好，您已成功进行会员卡充值。");
		firstMap.put("color", "#173177");
		dataMap.put("first", firstMap);
		
		Map<String, String> keynote1Map = new LinkedHashMap<String, String>();
		keynote1Map.put("value", "会员卡号");
		keynote1Map.put("color", "#173177");
		dataMap.put("accountType", keynote1Map);
		
		Map<String, String> keynote2Map = new LinkedHashMap<String, String>();
		keynote2Map.put("value", "11912345678");
		keynote2Map.put("color", "#173177");
		dataMap.put("account", keynote2Map);
		
		Map<String, String> keynote3Map = new LinkedHashMap<String, String>();
		keynote3Map.put("value", "50元");
		keynote3Map.put("color", "#173177");
		dataMap.put("amount", keynote3Map);
		
		Map<String, String> keynote4Map = new LinkedHashMap<String, String>();
		keynote4Map.put("value", "充值成功");
		keynote4Map.put("color", "#173177");
		dataMap.put("result", keynote4Map);
		
		Map<String, String> remarkMap = new LinkedHashMap<String, String>();
		remarkMap.put("value", "如有疑问，请致电13912345678联系我们。");
		remarkMap.put("color", "#173177");
		dataMap.put("remark", remarkMap);
		
		wrapMap.put("data", dataMap);
		
		String jsonMsg = JSONObject.toJSONString(wrapMap);
		Response<Long> response = AdvancedUtil.sendTemplateMsg(InitUtil.accessToken, jsonMsg);
		System.out.println("testSendTemplateMsg:" + JSON.toJSONString(response));
	}
	
	@Test
	public void testGetAutoreplayInfo() {
		JSONObject jsonObject = AdvancedUtil.getAutoreplayInfo(InitUtil.accessToken);
		System.out.println(jsonObject.toString());
	}

}
