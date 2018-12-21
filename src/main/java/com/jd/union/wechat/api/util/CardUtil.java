package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.model.WeixinQRCode;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信卡券接口
 * @author zhangjianhui
 *
 */
@Slf4j
public class CardUtil {
	/** 文本 */
	public static final String CODE_TYPE_TEXT = "CODE_TYPE_TEXT";
	/** 一维码 */
	public static final String CODE_TYPE_BARCODE = "CODE_TYPE_BARCODE";
	/** 二维码 */
	public static final String CODE_TYPE_QRCODE = "CODE_TYPE_QRCODE";
	/** 二维码无code显示 */
	public static final String CODE_TYPE_ONLY_QRCODE = "CODE_TYPE_ONLY_QRCODE";
	/** 一维码无code显示 */
	public static final String CODE_TYPE_ONLY_BARCODE = "CODE_TYPE_ONLY_BARCODE";
	
	/** 固定日期区间 */
	public static final String DATE_TYPE_FIX_TIME_RANGE = "DATE_TYPE_FIX_TIME_RANGE";
	/** 固定时长(自领取后按天算) */
	public static final String DATE_TYPE_FIX_TERM = "DATE_TYPE_FIX_TERM";
	
	/** 团购券类型 */
	public static final String CARD_TYPE_GROUPON = "GROUPON";
	/** 代金券类型 */
	public static final String CARD_TYPE_CASH = "CASH";
	/** 折扣券类型 */
	public static final String CARD_TYPE_DISCOUNT = "DISCOUNT";
	/** 礼品券类型 */
	public static final String CARD_TYPE_GIFT = "GIFT";
	/** 一般优惠券类型 */
	public static final String CARD_TYPE_GENERAL_COUPON = "GENERAL_COUPON";
	
	/**
	 * 上传卡券LOGO
	 * @param accessToken 接口访问凭证
	 * @param mediaFileUrl urll
	 * @return String
	 */
	public static String uploadImg(String accessToken, String mediaFileUrl){
		return CommonUtil.uploadImg(accessToken, mediaFileUrl);
	}
	
	/**
	 * 获取卡券颜色列表
	 * @param accessToken 接口访问凭证
	 * @return JSONArray
	 */
	public static JSONArray getColor(String accessToken){
		JSONArray jsonArr = new JSONArray();
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/card/getcolors?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (0 == errorCode) {
				jsonArr = jsonObj.getJSONArray("colors");
				log.info("获取卡券颜色列表成功");
			} else {
				log.error("获取卡券颜色列表失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return jsonArr;
	}
	
	/**
	 * 创建卡券
	 * @param accessToken 接口访问凭证
	 * @param jsonData json入参
	 * @return String
	 */
	public static String createCard(String accessToken, String jsonData){
		String cardId = "";
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (0 == errorCode) {
				cardId = jsonObj.getString("card_id");
				log.info("创建卡券成功 card_id:{}" + cardId);
			} else {
				log.error("创建卡券失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return cardId;
	}
	
	/**
	 * 创建卡券二维码
	 * @param accessToken 接口访问凭证
	 * @param jsonData jsondata
	 * @return WeixinQRCode
	 */
	public static WeixinQRCode createCardQR(String accessToken, String jsonData){
		WeixinQRCode weixinQRCode = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/card/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (0 == errorCode) {
				weixinQRCode = new WeixinQRCode();
				weixinQRCode.setTicket(jsonObj.getString("ticket"));
				weixinQRCode.setExpireSeconds(jsonObj.getInteger("expire_seconds"));
				weixinQRCode.setUrl(jsonObj.getString("url"));
				log.info("创建卡券二维码成功 ticket:{} expire_seconds:{}", weixinQRCode.getTicket(), weixinQRCode.getExpireSeconds());
			} else {
				log.error("创建卡券二维码失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return weixinQRCode;
	}
	
	/**
	 * 图文消息群发卡券HTML文本内容
	 * @param accessToken 接口访问凭证
	 * @param cardId cardId
	 * @return String
	 */
	public static String getCardHtml(String accessToken, String cardId){
		String content = "";
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"card_id\":\"%s\"}";
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, cardId));
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (0 == errorCode) {
				content = jsonObj.getString("content");
				log.info("图文消息群发卡券HTML文本内容成功 card_id:{}" + cardId);
			} else {
				log.error("图文消息群发卡券HTML文本内容失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return content;
	}
	
	/**
	 * 设置测试白名单
	 * @param accessToken 接口访问凭证
	 * @param jsonData jsonData
	 * @return boolean
	 */
	public static boolean setTestwhitelist(String accessToken, String jsonData){
		boolean result = false;
		String requestUrl = "https://api.weixin.qq.com/card/testwhitelist/set?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("设置测试白名单成功");
			} else {
				log.error("设置测试白名单失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 核销Code
	 * @param accessToken 接口访问凭证
	 * @param jsonData jsonData
	 * @return JSONObject
	 */
	public static JSONObject consumeCode(String accessToken, String jsonData){
		String requestUrl = "https://api.weixin.qq.com/card/code/consume?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (0 == errorCode) {
				log.info("核销Code成功");
			} else {
				log.error("核销Code失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return jsonObj;
	}
	
	/**
	 * 卡券Code解码
	 * @param accessToken  接口访问凭证
	 * @param encryptCode 加密code
	 * @return 解密code
	 */
	public static String decryptCode(String accessToken, String encryptCode){
		String code = "";
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/card/code/decrypt?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"encrypt_code\":\"%s\"}";
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, encryptCode));
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (0 == errorCode) {
				code = jsonObj.getString("code");
				log.info("卡券Code解码成功 encryptCode:{}" + encryptCode);
			} else {
				log.error("卡券Code解码失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return code;
	}
	
	/**
	 * 查询code
	 * @param accessToken 接口访问凭证
	 * @param code code
	 * @return 卡券明细
	 */
	public static JSONObject getCode(String accessToken, String code){
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/card/code/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"code\":\"%s\"}";
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, code));
		if (null != jsonObj) {
			int errorCode = jsonObj.getInteger("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if (0 == errorCode) {
				log.info("卡券查询code成功 code:{}" + code);
			} else {
				log.error("卡券查询code失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return jsonObj;
	}

}
