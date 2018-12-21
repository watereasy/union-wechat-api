package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.message.mass.MGFilter;
import com.jd.union.wechat.api.message.mass.MGNewsMsg;
import com.jd.union.wechat.api.message.mass.MNews;
import com.jd.union.wechat.api.message.mass.MPNewsMsg;
import com.jd.union.wechat.api.message.mass.MPVideoMsg;
import com.jd.union.wechat.api.message.mass.MVideo;
import com.jd.union.wechat.api.model.SNSUserInfo;
import com.jd.union.wechat.api.model.WeixinGroup;
import com.jd.union.wechat.api.model.WeixinQRCode;
import com.jd.union.wechat.api.model.WeixinUserList;

import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
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
		fail("Not yet implemented");
	}

	@Test
	public void testRefreshOauth2AccessToken() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckOauth2AccessToken() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSNSUserInfo() {
		//fail("Not yet implemented");
		/*String accessToken = "EH7kU5-UUXB3NwE6MbHR6mb-Fyen6QGBjRvMLmy0jUdjHhK1RmFdEReXuTgX7A1wir-4mdDSxxnUmln5m2aZxox-vqjuavHyhAl7l4SeNg8";
		System.out.println(InitUtil.accessToken);
		accessToken = InitUtil.accessToken;
		SNSUserInfo user = AdvancedUtil.getSNSUserInfo(accessToken, "oNI8hs5zE2DjCN58Mdpiw8xkQYks");*/
		SNSUserInfo user = AdvancedUtil.getSNSUserInfo(InitUtil.accessToken, "oR8rRvhSBAkn07aeeEmtTAg_ob8w");
		System.out.println(JSONObject.toJSONString(user));
	}

	@Test
	public void testCreateTemporaryQRCode() {
		WeixinQRCode wqr = AdvancedUtil.createTemporaryQRCode(InitUtil.accessToken, 600, 1);
		String ticket = wqr.getTicket();
		//int expireSecond = wqr.getExpireSeconds();
		System.out.println(AdvancedUtil.getQRCode(ticket, "D:/Download"));
	}

	@Test
	public void testCreatePermanentQRCode() {
		String ticket = AdvancedUtil.createPermanentQRCode(InitUtil.accessToken, 1);
		System.out.println(AdvancedUtil.getQRCode(ticket, "D:/Download"));
	}

	@Test
	public void testGetQRCode() {
		//fail("Not yet implemented");
	}

	@Test
	public void testLongUrl2shortUrl() {
		String longUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx63457c110481fdf2&redirect_uri=http%3a%2f%2ftlactivity.changyou.com%2ftl%2ftwCombine%2fauth.ncdo%3ffrom%3dQR&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
		AdvancedUtil.longUrl2shortUrl(InitUtil.accessToken, longUrl);
		// http://w.url.cn/s/Ae8hx2s
	}

	@Test
	public void testGetGroups() {
		List<WeixinGroup> weixinGroupList = AdvancedUtil.getGroups(InitUtil.accessToken);
		System.out.println(JSONArray.toJSONString(weixinGroupList));
	}

	@Test
	public void testQueryUserGroup() {
		WeixinUserList weixinUserList = UserUtil.getUserList(InitUtil.accessToken, "");
		for(String openId : weixinUserList.getOpenIdList()){
			System.out.println("openId:" + openId + " 所属分组=>" + AdvancedUtil.queryUserGroup(InitUtil.accessToken, openId));
		}
	}

	@Test
	public void testCreateGroup() {
		WeixinGroup wg = AdvancedUtil.createGroup(InitUtil.accessToken, "test1");
		if(wg != null){
			System.out.println("id:" + wg.getId() + "|name:" + wg.getName());
		}else{
			fail("请求异常！");
		}
	}

	@Test
	public void testUpdateGroup() {
		Assert.assertTrue(AdvancedUtil.updateGroup(InitUtil.accessToken, 100, "boolink"));
	}

	@Test
	public void testUpdateMemberGroup() {
		Assert.assertTrue(AdvancedUtil.updateMemberGroup(InitUtil.accessToken, "oNI8hs5eMajBHOdpPzpCXm-Q8CKw", 100));
	}
	
	@Test
	public void testUpdateMembersGroup() {
		List<String> openIdLis = new ArrayList<String>();
		openIdLis.add("oNI8hs3M_-Rwa5JQbN4Z1autC1Ck");
		openIdLis.add("oNI8hs5zE2DjCN58Mdpiw8xkQYks");
		Assert.assertTrue(AdvancedUtil.updateMembersGroup(InitUtil.accessToken, openIdLis, 100));
	}
	
	@Test
	public void testDeleteGroup() {
		// TODO 微信的返回码为{}有问题 会报错
		Assert.assertTrue(AdvancedUtil.deleteGroup(InitUtil.accessToken, 103));
	}

	@Test
	public void testUploadNews() {
		// 等同于素材管理MaterialUtil.addNews 方法
	}

	@Test
	public void testMassMsgByGroup() {
		// 分组群发图文,其他类型类似
		MGFilter filter = new MGFilter();
		// TODO 不支持is开头的属性
//		filter.setIs_to_all(false);
		filter.set_to_all(false);
		filter.setGroup_id("100");
		MNews mNews = new MNews();
		mNews.setMedia_id("ohBGsOcqzyMpbKzLN8DFKf747FeLNhxl4gK85QTW-pM");
		MGNewsMsg mNewsMsg = new MGNewsMsg();
		mNewsMsg.setFilter(filter);
		mNewsMsg.setMpnews(mNews);
		mNewsMsg.setMsgtype(MsgUtil.MASS_MESSAGE_TYPE_NEWS);
		System.out.println(JSONObject.toJSONString(mNewsMsg));
		//AdvancedUtil.massMsgByGroup(InitUtil.accessToken, JSONObject.fromObject(mNewsMsg).toString());
	}

	@Test
	public void testMassMsgByOpenID() {
		// TODO 视频是mpvideo(组群发)还是video(openId群发)？看来微信的文档真是外包写的
		// OpenID群发图文,其他类型类似
		List<String> openIDList = new ArrayList<String>();
		openIDList.add("oNI8hs5eMajBHOdpPzpCXm-Q8CKw");
		openIDList.add("oNI8hs3M_-Rwa5JQbN4Z1autC1Ck");
		// 图文
//		MNews mNews = new MNews();
//		mNews.setMedia_id("ohBGsOcqzyMpbKzLN8DFKf747FeLNhxl4gK85QTW-pM");
//		MPNewsMsg mpNewsMsg = new MPNewsMsg();
//		mpNewsMsg.setTouser(openIDList);
//		mpNewsMsg.setMpnews(mNews);
//		mpNewsMsg.setMsgtype(MsgUtil.MASS_MESSAGE_TYPE_NEWS);
//		AdvancedUtil.massMsgByOpenID(InitUtil.accessToken, JSONObject.fromObject(mpNewsMsg).toString());
		// 视频
		MVideo mVideo = new MVideo();
		mVideo.setMedia_id("zEET52sP3cqfNIj3qZJRPzBz0mo4Ho04mZKGfIIlkNU");
		MPVideoMsg mpVideoMsg = new MPVideoMsg();
		mpVideoMsg.setTouser(openIDList);
		mpVideoMsg.setMpvideo(mVideo);
		mpVideoMsg.setMsgtype(MsgUtil.MASS_MESSAGE_TYPE_VIDEO);
		AdvancedUtil.massMsgByOpenID(InitUtil.accessToken, JSONObject.toJSONString(mpVideoMsg));
	}

	@Test
	public void testDeleteMassMsg() {
		String jsonMsg = "{\"msg_id\":30124}";
		AdvancedUtil.deleteMassMsg(InitUtil.accessToken, jsonMsg);
	}

	@Test
	public void testMassMsgPreview() {
		List<String> openIDList = new ArrayList<String>();
		openIDList.add("oNI8hs5eMajBHOdpPzpCXm-Q8CKw"); // 示例中只有一个，不知道不可以群发多人预览
		MNews mNews = new MNews();
		mNews.setMedia_id("ohBGsOcqzyMpbKzLN8DFKf747FeLNhxl4gK85QTW-pM");
		MPNewsMsg mpNewsMsg = new MPNewsMsg();
		mpNewsMsg.setTouser(openIDList);
		mpNewsMsg.setMpnews(mNews);
		mpNewsMsg.setMsgtype(MsgUtil.MASS_MESSAGE_TYPE_NEWS);
		// "touser":["aaaaa"]=>"touser":"aaaaa"
		AdvancedUtil.massMsgPreview(InitUtil.accessToken, JSONObject.toJSONString(mpNewsMsg).replace("[", "").replace("]", ""));
		// 上述JSON数据中的touser字段都可以改为towxname，这样就可以针对微信号进行预览（而非openID），towxname和touser同时赋值时，以towxname优先
	}

	@Test
	public void testQueryMassMsgStatus() {
		// TODO 未实际测试
		String jsonMsg = "{\"msg_id\":30124}";
		AdvancedUtil.queryMassMsgStatus(InitUtil.accessToken, jsonMsg);
	}

	@Test
	@Deprecated
	public void testGetMassVideo() {
		// 此方法已废弃，详情见素材管理
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
		String templateId = AdvancedUtil.addTemplateMsg(InitUtil.accessToken, "TM00154");
		System.out.println(templateId);
	}
	
	@Test
	public void testGetAllTempalte() {
		JSONObject jsonObj = AdvancedUtil.getAllTempalte(InitUtil.accessToken);
		System.out.println(jsonObj.toString());
	}
	
	@Test
	public void testDeleteTempalte() {
		boolean result = AdvancedUtil.deleteTempalte(InitUtil.accessToken, "MemTJRsAgxGbULTnctj9QAQ0USUM7t5tLJBeLLTg0X4");
		System.out.println(result);
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
		AdvancedUtil.sendTemplateMsg(InitUtil.accessToken, jsonMsg);
	}
	
	@Test
	public void testGetAutoreplayInfo() {
		JSONObject jsonObject = AdvancedUtil.getAutoreplayInfo(InitUtil.accessToken);
		System.out.println(jsonObject.toString());
	}

}
