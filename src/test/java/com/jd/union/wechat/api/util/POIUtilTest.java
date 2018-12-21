package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.model.WeixinPOI;
import com.jd.union.wechat.api.model.WeixinPhoto;

import common.InitUtil;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POIUtilTest {

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
	public void testUploadImg() {
		String mediaFileUrl = "http://pic.baike.soso.com/p/20140221/20140221045003-817247685.jpg";
		System.out.println(POIUtil.uploadImg(InitUtil.accessToken, mediaFileUrl));
	}
	
	@Test
	public void testAddPOI(){
		WeixinPOI poi = new WeixinPOI();
		poi.setSid("33788392");
		poi.setBusiness_name("麦当劳");
		poi.setBranch_name("艺苑路店");
		poi.setProvince("广东省");
		poi.setCity("广州市");
		poi.setDistrict("海珠区");
		poi.setAddress("艺苑路11 号");
		poi.setTelephone("020-12345678");
		List<String> caLis = new ArrayList<>();
		caLis.add("美食,小吃快餐");
		poi.setCategories(caLis);
		poi.setOffset_type(1);
		poi.setLatitude(115.32375);
		poi.setLatitude(25.097486);
		List<WeixinPhoto> phoneLis = new ArrayList<>();
		WeixinPhoto photo = new WeixinPhoto();
		photo.setPhoto_url("http://img.ivsky.com/img/bizhi/slides/201507/19/monkey_king_hero_is_back-011.jpg");
		phoneLis.add(photo);
		WeixinPhoto photo2 = new WeixinPhoto();
		photo2.setPhoto_url("http://img.ivsky.com/img/bizhi/slides/201506/13/insurgent-005.jpg");
		phoneLis.add(photo2);
		poi.setPhoto_list(phoneLis);
		poi.setRecommend("麦辣鸡腿堡套餐，麦乐鸡，全家桶");
		poi.setSpecial("免费wifi，外卖服务");
		poi.setIntroduction("麦当劳是全球大型跨国连锁餐厅，1940 年创立于美国，在世界上大约拥有3 万间分店。主要售卖汉堡包，以及薯条、炸鸡、汽水、冰品、沙拉、 水果等快餐食品");
		poi.setOpen_time("8:00-20:00");
		poi.setAvg_price(35);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("base_info", poi);
		Map<String, Object> bussMap = new HashMap<>();
		bussMap.put("business", baseMap);
		
		JSONObject jsonObj = JSONObject.parseObject(JSON.toJSONString(bussMap));
		JSONObject jsonBuss = jsonObj.getJSONObject("business");
		JSONObject jsonBase = jsonBuss.getJSONObject("base_info");
		WeixinPOI wxPOI = JSONObject.parseObject(jsonBase.toJSONString(), WeixinPOI.class);
		System.out.println(wxPOI.getCategories().toString());
		System.out.println(jsonBuss);
		System.out.println(jsonBase);
		String jsonData = jsonObj.toString();
		System.out.println(jsonData);
		Assert.assertTrue(POIUtil.addPOI(InitUtil.accessToken, jsonData));
	}
	
	
	@Test
	public void testGetPOI(){
		String poiId = "271262077";
		WeixinPOI poi = POIUtil.getPOI(InitUtil.accessToken, poiId);
		Assert.assertTrue(StringUtils.isNotBlank(poi.getBusiness_name()));
	}
	
	@Test
	public void testGetPOILis(){
		List<WeixinPOI> poiLis = new ArrayList<>();
		int begin = 0;
		int limit = 10;
		JSONObject jsonData = POIUtil.getPOILis(InitUtil.accessToken, begin, limit);
		int totalCount = 0;
		JSONObject jsonObj;
		JSONObject jsonBase;
		WeixinPOI poi;
		if(jsonData != null){
			if(jsonData.getInteger("errcode") == 0){
				JSONArray jsonArr = jsonData.getJSONArray("business_list");
				for(int i=0;i<jsonArr.size();i++){
					jsonObj = jsonArr.getJSONObject(0);
					jsonBase = jsonObj.getJSONObject("base_info");
					poi = JSONObject.parseObject(jsonBase.toJSONString(), WeixinPOI.class);
					poiLis.add(poi);
				}
				totalCount = jsonData.getInteger("total_count");
			}
		}
		
		System.out.println("门店总数量:" + totalCount);
		Assert.assertTrue(poiLis.size() > 0);
	}

	@Test
	public void testUpdatePOI(){
		WeixinPOI poi = new WeixinPOI();
		poi.setPoi_id("271864249");
		//poi.setSid("33788392");
		//poi.setBusiness_name("麦当劳");
		//poi.setBranch_name("艺苑路店");
		//poi.setProvince("广东省");
		//poi.setCity("广州市");
		//poi.setDistrict("海珠区");
		//poi.setAddress("艺苑路11 号");
		poi.setTelephone("020-12345678");
		//List<String> caLis = new ArrayList<String>();
		//caLis.add("美食,小吃快餐");
		//poi.setCategories(caLis);
		//poi.setOffset_type(1);
		//poi.setLatitude(115.32375);
		//poi.setLatitude(25.097486);
		List<WeixinPhoto> phoneLis = new ArrayList<>();
		WeixinPhoto photo = new WeixinPhoto();
		photo.setPhoto_url("http://img.ivsky.com/img/bizhi/slides/201507/19/monkey_king_hero_is_back-011.jpg");
		phoneLis.add(photo);
		WeixinPhoto photo2 = new WeixinPhoto();
		photo2.setPhoto_url("http://img.ivsky.com/img/bizhi/slides/201506/13/insurgent-005.jpg");
		phoneLis.add(photo2);
		WeixinPhoto photo3 = new WeixinPhoto();
		photo3.setPhoto_url("http://img.ivsky.com/img/bizhi/slides/201506/13/insurgent-005.jpg");
		phoneLis.add(photo3);
		poi.setPhoto_list(phoneLis);
		poi.setRecommend("麦辣鸡腿堡套餐，麦乐鸡，全家桶");
		poi.setSpecial("免费wifi，外卖服务");
		poi.setIntroduction("麦当劳是全球大型跨国连锁餐厅，1940 年创立于美国，在世界上大约拥有3 万间分店。主要售卖汉堡包，以及薯条、炸鸡、汽水、冰品、沙拉、 水果等快餐食品");
		poi.setOpen_time("8:00-20:00");
		poi.setAvg_price(350);
		
		Map<String, Object> baseMap = new HashMap<>();
		baseMap.put("base_info", poi);
		Map<String, Object> bussMap = new HashMap<>();
		bussMap.put("business", baseMap);
		JSONObject jsonObj = JSONObject.parseObject(JSON.toJSONString(bussMap));
		String jsonData = jsonObj.toString();
		System.out.println(jsonData);
		Assert.assertTrue(POIUtil.updatePOI(InitUtil.accessToken, jsonData));
	}
	
	@Test
	public void testDelPOI(){
		String poiId = "271262077";
		Assert.assertTrue(POIUtil.delPOI(InitUtil.accessToken, poiId));
	}
	
	@Test
	public void testGetPOICategory(){
		Assert.assertTrue(POIUtil.getPOICategory(InitUtil.accessToken).size() > 0);
	}
	
}
