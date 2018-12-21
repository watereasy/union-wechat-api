package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.card.Card;
import com.jd.union.wechat.api.card.CardQR;
import com.jd.union.wechat.api.card.DateInfo;
import com.jd.union.wechat.api.card.GrouponCard;
import com.jd.union.wechat.api.card.Sku;
import com.jd.union.wechat.api.model.WeixinAction;
import com.jd.union.wechat.api.model.WeixinQRCode;

import common.InitUtil;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardUtilTest {

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
	public void testGetColor() {
		String accessToken = InitUtil.accessToken;
		JSONArray jsonArr = CardUtil.getColor(accessToken);
		Assert.assertTrue(jsonArr.size() > 0);
		for(int i=0;i<jsonArr.size();i++){
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			System.out.println("name:" + jsonObj.getString("name") + "|value:" + jsonObj.getString("value"));
		}
	}
	
	@Test
	public void testCreateCard(){
		// 卡券详细信息长度限制请参照相关文档 => http://mp.weixin.qq.com/wiki/8/b7e310e7943f7763450eced91fa793b0.html
		Card card = new Card();
		card.setLogo_url("http://mmbiz.qpic.cn/mmbiz/iaL1LJM1mF9aRKPZJkmG8xXhiaHqkKSVMMWeN3hLut7X7hicFNjakmxibMLGWpXrEXB33367o7zHN0CwngnQY7zb7g/0");
		card.setBrand_name("海底捞");
		card.setCode_type(CardUtil.CODE_TYPE_TEXT);
		card.setTitle("132元双人火锅套餐");
		card.setSub_title("周末狂欢必备");
		card.setColor("Color010"); // 卡券颜色接口
		card.setNotice("使用时向服务员出示此券");
		card.setService_phone("020-88888888");
		card.setDescription("不可与其他优惠同享\n如需团购券发票，请在消费时向商户提出\n店内均可使用，仅限堂食");
		DateInfo data_info = new DateInfo();
		data_info.setType(CardUtil.DATE_TYPE_FIX_TIME_RANGE);
		data_info.setBegin_timestamp(1397577600);
		data_info.setEnd_timestamp(1422724261);
		card.setDate_info(data_info);
		Sku sku = new Sku();
		sku.setQuantity(500000);
		card.setSku(sku);
		card.setGet_limit(3);
		card.setUse_custom_code(false);
		card.setBind_openid(false);
		card.setCan_share(true);
		card.setCan_give_friend(true);
		List<Integer> location_id_list = new ArrayList<Integer>();
		location_id_list.add(123);
		location_id_list.add(12321);
		location_id_list.add(345345);
		card.setLocation_id_list(location_id_list);
		card.setCustom_url_name("立即使用");
		card.setCustom_url("http://www.qq.com");
		card.setCustom_url_sub_title("6个汉字tips");
		card.setPromotion_url_name("http://www.qq.com");
		card.setPromotion_url("http://www.qq.com");
		card.setSource("大众点评");
		
		GrouponCard gCard = new GrouponCard();
		gCard.setBase_info(card);
		gCard.setDeal_detail("以下锅底2选1（有菌王锅、麻辣锅、大骨锅、番茄锅、清补凉锅、酸菜鱼锅可选）：\n大锅1份 12元\n小锅2份 16元 ");
		String cardType = CardUtil.CARD_TYPE_GROUPON;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("card_type", cardType);
		map.put(cardType.toLowerCase(), gCard);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("card", map);
		
		String jsonData = JSONObject.toJSONString(map2);
		System.out.println(jsonData);
		String cardId = CardUtil.createCard(InitUtil.accessToken, jsonData);
		Assert.assertTrue(StringUtils.isNotBlank(cardId));
	}

	@Test
	public void testCreateCardQR(){
		CardQR qr = new CardQR();
		qr.setCard_id("pjZ8Yt4kGlkdTA-RGXZTKLA0iueE");
		qr.setOpenid("ojZ8YtyVyr30HheH3CM73y7h4jJE");
		qr.set_unique_code(false);
		qr.setOuter_id(1);
		System.out.println(JSONObject.toJSONString(qr));
		
		JSONObject action_info = new JSONObject();
		action_info.put("card", JSONObject.toJSONString(qr)); // 过滤
		
		WeixinAction action = new WeixinAction();
		action.setAction_name("QR_CARD");
		action.setExpire_seconds(800);
		action.setAction_info(action_info);
		String jsonData = JSONObject.toJSONString(action); // 过滤
		System.out.println(jsonData); 
		WeixinQRCode qrCode = CardUtil.createCardQR(InitUtil.accessToken, jsonData);
		Assert.assertNotNull(qrCode);
	}
	
	@Test
	public void testGetCardHtml(){
		String cardId = "pFS7Fjg8kV1IdDz01r4SQwMkuCKc";
		String content = CardUtil.getCardHtml(InitUtil.accessToken, cardId);
		Assert.assertTrue(StringUtils.isNotBlank(content));
	}
	
	@Test
	public void testSetTestwhitelist(){
		// 以下数据自行找接口获取
		List<String> openidLis = new ArrayList<String>();
		openidLis.add("o1Pj9jmZvwSyyyyyyBa4aULW2mA");
		openidLis.add("o1Pj9jmZvxxxxxxxxxULW2mA");
		List<String> usernameLis = new ArrayList<String>();
		usernameLis.add("afdvvf");
		usernameLis.add("abcd");
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("openid", openidLis);
		jsonMap.put("username", usernameLis);
		
		String jsonData = JSONObject.toJSONString(jsonMap);
		System.out.println(jsonData);
		Assert.assertTrue(CardUtil.setTestwhitelist(InitUtil.accessToken, jsonData));
	}
	
	@Test
	public void testConsumeCard(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "12312313");
		map.put("card_id", "pFS7Fjg8kV1IdDz01r4SQwMkuCKc"); // 卡券ID。创建卡券时use_custom_code填写true时必填。非自定义Code不必填写
		String jsonData = JSONObject.toJSONString(map);
		JSONObject jsonObj = CardUtil.consumeCode(InitUtil.accessToken, jsonData);
		if(jsonObj != null){
			// TODO 怎样判断
			String openId = jsonObj.getString("openid");
		}
	}
	
	@Test
	public void testDecryptCard(){
		String encryptCode = "XXIzTtMqCxwOaawoE91+VJdsFmv7b8g0VZIZkqf4GWA60Fzpc8ksZ/5ZZ0DVkXdE";
		String code = CardUtil.decryptCode(InitUtil.accessToken, encryptCode);
		Assert.assertTrue(StringUtils.isNotBlank(code));
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testGetCode(){
		String code = "110201201245";
		JSONObject jsonObj = CardUtil.getCode(InitUtil.accessToken, code);
		System.out.println(jsonObj.toString());
		if(jsonObj != null){
			int errCode = jsonObj.getInteger("errcode");
			if(errCode ==  0){
				String openId = jsonObj.getString("openid");
				JSONObject jsonObj2 = jsonObj.getJSONObject("card");
				String cardId = jsonObj2.getString("card_id");
				int beginTime = jsonObj2.getInteger("begin_time");
				int endTime = jsonObj2.getInteger("end_time");
			}
		}
	}
	
}
