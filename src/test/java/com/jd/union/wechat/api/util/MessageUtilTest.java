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
import com.jd.union.wechat.api.model.MassResult;

import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
		//MessageUtil.massMsgByGroup(InitUtil.accessToken, JSONObject.fromObject(mNewsMsg).toString());
	}

	@Test
	public void testMassMsgByOpenID() {
		// TODO 视频是mpvideo(组群发)还是video(openId群发)？看来微信的文档真是外包写的
		// OpenID群发图文,其他类型类似
		List<String> openIDList = new ArrayList<>();
		openIDList.add("oV7Lcjpo037FHR4tMtq973wFze7E");
		openIDList.add("oV7LcjntWg-bSALoPjC0szqaJX9w");
		// 图文
		MNews mNews = new MNews();
		mNews.setMedia_id("BiN4sqP0jD9V6WLgfNb1LkMsv4JisVcu8z0u4ytabtY");
		MPNewsMsg mpNewsMsg = new MPNewsMsg();
		mpNewsMsg.setTouser(openIDList);
		mpNewsMsg.setMpnews(mNews);
		mpNewsMsg.setSend_ignore_reprint(1);
		mpNewsMsg.setMsgtype(MsgUtil.MASS_MESSAGE_TYPE_NEWS);
		System.out.println(JSONObject.toJSONString(mpNewsMsg));
		Response<MassResult> response = MessageUtil.massMsgByOpenID(InitUtil.accessToken, JSONObject.toJSONString(mpNewsMsg));
		System.out.println(JSON.toJSONString(response));
		// 视频
//		MVideo mVideo = new MVideo();
//		mVideo.setMedia_id("zEET52sP3cqfNIj3qZJRPzBz0mo4Ho04mZKGfIIlkNU");
//		MPVideoMsg mpVideoMsg = new MPVideoMsg();
//		mpVideoMsg.setTouser(openIDList);
//		mpVideoMsg.setMpvideo(mVideo);
//		mpVideoMsg.setMsgtype(MsgUtil.MASS_MESSAGE_TYPE_VIDEO);
//		MessageUtil.massMsgByOpenID(InitUtil.accessToken, JSONObject.toJSONString(mpVideoMsg));
	}

	@Test
	public void testDeleteMassMsg() {
		String jsonMsg = "{\"msg_id\":30124}";
		MessageUtil.deleteMassMsg(InitUtil.accessToken, jsonMsg);
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
		MessageUtil.massMsgPreview(InitUtil.accessToken, JSONObject.toJSONString(mpNewsMsg).replace("[", "").replace("]", ""));
		// 上述JSON数据中的touser字段都可以改为towxname，这样就可以针对微信号进行预览（而非openID），towxname和touser同时赋值时，以towxname优先
	}

	@Test
	public void testQueryMassMsgStatus() {
		// TODO 未实际测试
		String jsonMsg = "{\"msg_id\":30124}";
		MessageUtil.queryMassMsgStatus(InitUtil.accessToken, jsonMsg);
	}

	@Test
	@Deprecated
	public void testGetMassVideo() {
		// 此方法已废弃，详情见素材管理
	}

}
