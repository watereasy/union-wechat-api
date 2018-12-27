package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.model.UserTag;
import com.jd.union.wechat.api.model.WeixinUserInfo;
import com.jd.union.wechat.api.model.WeixinUserList;

import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class UserUtilTest {

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
	public void testCreateTag() {
		Response<UserTag> response = UserUtil.createTag(InitUtil.accessToken, "test1");
		System.out.println("testCreateTag:" + JSON.toJSONString(response));
		UserTag wg = response.getResult();
		if(wg != null){
			System.out.println("id:" + wg.getId() + "|name:" + wg.getName());
		}else{
			fail("请求异常！");
		}
	}
	
	@Test
	public void testGetTag() {
		Response<List<UserTag>> response = UserUtil.getTags(InitUtil.accessToken);
		System.out.println("testGetTag:" + JSON.toJSONString(response));
		List<UserTag> userTagList = response.getResult();
		System.out.println(JSONArray.toJSONString(userTagList));
	}

	@Test
	public void testUpdateTag() {
		Response response = UserUtil.updateTag(InitUtil.accessToken, 100, "boolink111");
		System.out.println("testUpdateTag:" + JSON.toJSONString(response));
	}
	
	@Test
	public void testDeleteTag() {
		Response response = UserUtil.deleteTag(InitUtil.accessToken, 100);
		System.out.println("testDeleteTag:" + JSON.toJSONString(response));
	}
	
	@Test
	public void testGetUserTag() {
		Response<WeixinUserList> response = UserUtil.getTag(InitUtil.accessToken, 101, "");
		System.out.println("testGetUserTag:" + JSON.toJSONString(response));
		WeixinUserList weixinUserList = response.getResult();
		//System.out.println("总关注用户数：" + weixinUserList.getTotal());
		System.out.println("本次获取用户数：" + weixinUserList.getCount());
		System.out.println("OpenID列表：" + weixinUserList.getOpenIdList().toString());
		System.out.println("next_openid：" + weixinUserList.getNextOpenId());
	}

	@Test
	public void tesBatchTagging() {
		List<String> openIdLis = new ArrayList<String>();
		openIdLis.add("oNI8hs3M_-Rwa5JQbN4Z1autC1Ck");
		openIdLis.add("oNI8hs2nd1hNB2fv-IlQQI6N3DzA");
		Response response = UserUtil.batchTagging(InitUtil.accessToken, 105, openIdLis);
		System.out.println("tesBatchTagging:" + JSON.toJSONString(response));
	}
	
	@Test
	public void testBatchUntagging() {
		List<String> openIdLis = new ArrayList<String>();
		openIdLis.add("oNI8hs3M_-Rwa5JQbN4Z1autC1Ck");
		openIdLis.add("oNI8hs2nd1hNB2fv-IlQQI6N3DzA");
		Response response = UserUtil.batchUntagging(InitUtil.accessToken, 105, openIdLis);
		System.out.println("testBatchUntagging:" + JSON.toJSONString(response));
	}
	
	@Test
	public void testGetTagidList() {
		Response<List<Integer>> response = UserUtil.getTagidList(InitUtil.accessToken, "oNI8hs2nd1hNB2fv-IlQQI6N3DzA");
		System.out.println("testGetTagidList:" + JSON.toJSONString(response));
		List<Integer> userTagList = response.getResult();
		System.out.println(JSONArray.toJSONString(userTagList));
	}
	
	@Test
	public void testSetUserRemarkName() {
		Response response = UserUtil.setUserRemarkName(InitUtil.accessToken, "oNI8hs5zE2DjCN58Mdpiw8xkQYks", "艾莎");
		System.out.println("testSetUserRemarkName:" + JSON.toJSONString(response));
	}
	
	@Test
	public void testGetUserInfo() {
		Response<WeixinUserList> response = UserUtil.getUserList(InitUtil.accessToken, "");
		WeixinUserList weixinUserList = response.getResult();
		for(String openId : weixinUserList.getOpenIdList()){
			Response<WeixinUserInfo> response1 = UserUtil.getUserInfo(InitUtil.accessToken, openId, "");
			WeixinUserInfo wui = response1.getResult();
			System.out.println(wui.getOpenid());
			//System.out.println(JSONObject.toJSONString(wui).toString());
			//break;
		}
	}
	
	@Test
	public void testGetUserInfo2() {
		String openId = "oV7Lcjpo037FHR4tMtq973wFze7E";
		Response<WeixinUserInfo> response  = UserUtil.getUserInfo(InitUtil.accessToken, openId, "");
		System.out.println("testGetUserInfo2:" + JSON.toJSONString(response));
		WeixinUserInfo wui = response.getResult();
		System.out.println(JSONObject.toJSONString(wui));
	}
	
	@Test
	public void testgetUserInfoBatch() {
		List<String> openIdLis = new ArrayList<String>();
		openIdLis.add("oNI8hs5zE2DjCN58Mdpiw8xkQYks");
		openIdLis.add("oNI8hs3fJNbbGzvIXwTeU_9EjUVI");
		openIdLis.add("oNI8hs3M_-Rwa5JQbN4Z1autC1Ck");
		Response<List<WeixinUserInfo>> response = UserUtil.getUserInfoBatch(InitUtil.accessToken, openIdLis, "");
		List<WeixinUserInfo> wuiLis = response.getResult();
		for(WeixinUserInfo wui : wuiLis){
			System.out.println(JSONObject.toJSONString(wui));
		}
	}
	
	@Test
	public void testgetUserInfoBatch2() {
		List<String> openIdLis = new ArrayList<String>();
		/*openIdLis.add("oR8rRvg--3F_jyl4jOwExub322lg");
		openIdLis.add("oR8rRvg-2rpe7LDeQ8hUWqEPaon8");
		openIdLis.add("oR8rRvg-3CWYoMpL8kCTbzSku2_c");
		openIdLis.add("oR8rRvg-3Wb7rwAScq80OKpgmCcY");
		openIdLis.add("oR8rRvg-3tpU_26BpMLxqjjy-BGo");
		openIdLis.add("oR8rRvg-3zgPTTgtLI9EEMRzuk8o");
		openIdLis.add("oR8rRvg-4EN2toyf1pfyE20h4QQw");
		openIdLis.add("oR8rRvg-4XxGlj-hbOXZbzjo4lHU");
		openIdLis.add("oR8rRvg-4lz9uOxgcTnfYsJf1Nns");
		openIdLis.add("oR8rRvhQD_a6S1qm7h2aLJj_AuZc");*/
		openIdLis.add("oR8rRvteSocpyAMs3n4f7UbRYQy8");
		openIdLis.add("oR8rRvhSBAkn07aeeEmtTAg_ob8w");
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		Response<List<WeixinUserInfo>> response = UserUtil.getUserInfoBatch(InitUtil.accessToken, openIdLis, "");
		List<WeixinUserInfo> wuiLis = response.getResult();
		for(WeixinUserInfo user : wuiLis){
			System.out.println(JSONObject.toJSONString(user));
			//System.out.println(user.getUnionid() + "||" + user.getOpenid());
			
			Object[] objArr = {user.getUnionid(), user.getOpenid()}; 
			batchArgs.add(objArr);
		}
		System.out.println(batchArgs);
	}

	@Test
	public void testGetUserList() {
		Response<WeixinUserList> response = UserUtil.getUserList(InitUtil.accessToken, "");
		WeixinUserList weixinUserList = response.getResult();
		System.out.println("总关注用户数：" + weixinUserList.getTotal());
		System.out.println("本次获取用户数：" + weixinUserList.getCount());
		System.out.println("OpenID列表：" + weixinUserList.getOpenIdList().toString());
		System.out.println("next_openid：" + weixinUserList.getNextOpenId());
	}
	
	@Test
	public void testGetBlackList() {
		Response<WeixinUserList> response = UserUtil.getBlackList(InitUtil.accessToken, "");
		WeixinUserList weixinUserList = response.getResult();
		System.out.println("总黑名单用户数：" + weixinUserList.getTotal());
		System.out.println("本次获取用户数：" + weixinUserList.getCount());
		System.out.println("OpenID列表：" + weixinUserList.getOpenIdList().toString());
		System.out.println("next_openid：" + weixinUserList.getNextOpenId());
	}
	
	@Test
	public void testBatchBlackList() {
		List<String> openIdLis = new ArrayList<String>();
		openIdLis.add("oNI8hs3M_-Rwa5JQbN4Z1autC1Ck");
		openIdLis.add("oNI8hs2nd1hNB2fv-IlQQI6N3DzA");
		Response response = UserUtil.batchBlackList(InitUtil.accessToken, openIdLis);
		System.out.println("testBatchBlackList:" + JSON.toJSONString(response));
	}
	
	@Test
	public void testBatchUnblackList() {
		List<String> openIdLis = new ArrayList<String>();
		openIdLis.add("oNI8hs3M_-Rwa5JQbN4Z1autC1Ck");
		openIdLis.add("oNI8hs2nd1hNB2fv-IlQQI6N3DzA");
		Response response = UserUtil.batchUnblackList(InitUtil.accessToken, openIdLis);
		System.out.println("testBatchUnblackList:" + JSON.toJSONString(response));
	}
}
