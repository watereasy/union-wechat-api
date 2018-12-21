package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.model.WeixinPOI;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信门店接口
 * @author zhangjianhui
 *
 */
@Slf4j
public class POIUtil {
	
	/**
	 * 上传图片
	 * @param accessToken 接口访问凭证
	 * @param mediaFileUrl url
	 * @return String
	 */
	public static String uploadImg(String accessToken, String mediaFileUrl){
		return CommonUtil.uploadImg(accessToken, mediaFileUrl);
	}
	
	/**
	 * 创建门店
	 * @param accessToken 接口访问凭证
	 * @param jsonData jsonData
	 * @return boolean
	 */
	public static boolean addPOI(String accessToken, String jsonData){
		boolean result = false;
		// 拼装请求地址
		String requestUrl = "http://api.weixin.qq.com/cgi-bin/poi/addpoi?access_token=TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (errorCode == 0) {
				result = true;
				log.info("创建门店成功");
			} else {
				log.error("创建门店失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 查询门店信息
	 * @param accessToken 接口访问凭证
	 * @param poiId 门店Id
	 * @return WeixinPOI
	 */
	public static WeixinPOI getPOI(String accessToken, String poiId){
		WeixinPOI poi = new WeixinPOI();
		// 拼装请求地址
		String requestUrl = "http://api.weixin.qq.com/cgi-bin/poi/getpoi?access_token=TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"poi_id\":\"%s\"}";
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, poiId));
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (errorCode == 0) {
				JSONObject jsonBuss = jsonObj.getJSONObject("business");
				JSONObject jsonBase = jsonBuss.getJSONObject("base_info");
				poi = JSONObject.parseObject(jsonBase.toJSONString(), WeixinPOI.class);
				log.info("查询门店信息成功poiId：{}", poiId);
			} else {
				log.error("查询门店信息失败" + poiId + " errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return poi;
	}
	
	/**
	 * 查询门店列表
	 * @param accessToken 接口访问凭证
	 * @param begin 开始位置，0 即为从第一条开始查询
	 * @param limit 回数据条数，最大允许50，默认为20
	 * @return JSONObject
	 */
	public static JSONObject getPOILis(String accessToken, int begin, int limit){
		// 拼装请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/poi/getpoilist?access_token=TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"begin\":%d, \"limit\":%d}";
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, begin, limit));
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (errorCode == 0) {
				log.info("查询门店列表成功");
			} else {
				log.error("查询门店列表失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return jsonObj;
	}
	
	/**
	 * 修改门店服务信息
	 * @param accessToken 接口访问凭证
	 * @param jsonData jsonData
	 * @return boolean
	 */
	public static boolean updatePOI(String accessToken, String jsonData){
		boolean result = false;
		// 拼装请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/poi/updatepoi?access_token=TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (errorCode == 0) {
				result = true;
				log.info("修改门店服务信息成功");
			} else {
				log.error("修改门店服务信息失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 删除门店
	 * @param accessToken 接口访问凭证
	 * @param poiId 门店Id
	 * @return bllean
	 */
	public static boolean delPOI(String accessToken, String poiId){
		boolean result = false;
		// 拼装请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/poi/delpoi?access_token=TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"poi_id\":\"%s\"}";
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, poiId));
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (errorCode == 0) {
				result = true;
				log.info("删除门店成功");
			} else {
				log.error("删除门店失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 门店类目表
	 * @param accessToken 接口访问凭证
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getPOICategory(String accessToken){
		List<String> resultLis = new ArrayList<>();
		// 拼装请求地址
		String requestUrl = "http://api.weixin.qq.com/cgi-bin/api_getwxcategory?access_token=TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", null);
		if (null != jsonObj) {
			if (jsonObj.containsKey("category_list")) {
				resultLis = JSONArray.parseArray(jsonObj.getJSONArray("category_list").toJSONString(), String.class);
				log.info("查询门店列表成功");
			} else {
				int errorCode = jsonObj.getInteger("errcode");
				String errorMsg = jsonObj.getString("errmsg");
				log.error("查询门店列表失败errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return resultLis;
	}
	
}
