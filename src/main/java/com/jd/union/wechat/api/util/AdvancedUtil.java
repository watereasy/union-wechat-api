package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.factory.ResponseFactory;
import com.jd.union.wechat.api.model.MassResult;
import com.jd.union.wechat.api.model.SNSUserInfo;
import com.jd.union.wechat.api.model.WeixinMedia;
import com.jd.union.wechat.api.model.WeixinOauth2Token;
import com.jd.union.wechat.api.model.WeixinQRCode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import lombok.extern.slf4j.Slf4j;

/**
 * 高级接口工具类
 *
 */
@Slf4j
public class AdvancedUtil {

	/** 网页授权access_token(GET) */
	private static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	/** 刷新网页授权access_token(GET) */
	private static final String OAUTH2_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	/** 授权凭证（access_token）是否有效(GET) */
	private static final String OAUTH2_CHECK_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
	/** 拉取用户信息(需scope为 snsapi_userinfo)(GET) */
	private static final String SNS_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	/** 创建二维码(POST) */
	private static final String QRCODE_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
	/** 换取二维码(GET) */
	private static final String QRCODE_GET_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	/** 长链接转短链(POST) */
	private static final String SHORTURL_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";

	/** 设置所属行业(POST) */
	private static final String TEMPLATE_API_SET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	/** 获取设置的行业信息(GET) */
	private static final String TEMPLATE_GET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
	/** 获得模板ID(POST) */
	private static final String TEMPLATE_API_ADD_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
	/** 获取模板列表(GET) */
	private static final String TEMPLATE_GET_ALL_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
	/** 删除模板(POST) */
	private static final String TEMPLATE_DEL_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN";
	/** 发送模板消息(POST) */
	private static final String TEMPLATE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	/** 获取公众号的自动回复规则(GET) */
	private static final String MESSAGE_GET_CURRENT_AUTOREPLY_INFO = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=ACCESS_TOKEN";


	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code 请求code
	 * @return WeixinAouth2Token
	 */
	@SuppressWarnings({"unchecked"})
	public static Response<WeixinOauth2Token> getOauth2AccessToken(String appId, String appSecret, String code) {
		Response response = ResponseFactory.getInstance();
		WeixinOauth2Token wat;
		// 拼接请求地址
		String requestUrl = OAUTH2_ACCESS_TOKEN_URL.replace(Constants.APPID, appId).replace(Constants.SECRET, appSecret).replace(Constants.CODE, code);
		// 获取网页授权凭证
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		if (null != jsonObject) {
			try {
				wat = getWeixinOauth2Token(jsonObject);
				response.success(wat);
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errorMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errorCode, errorMsg);
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}",errorCode, errorMsg);
			}
		}
		return response;
	}
	
	/**
	 * 刷新网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param refreshToken 刷新token
	 * @return WeixinAouth2Token
	 */
	@SuppressWarnings({"unchecked"})
	public static Response<WeixinOauth2Token> refreshOauth2AccessToken(String appId, String refreshToken) {
		Response response = ResponseFactory.getInstance();
		WeixinOauth2Token wat;
		// 拼接请求地址
		String requestUrl = OAUTH2_REFRESH_TOKEN_URL.replace(Constants.APPID, appId).replace(Constants.REFRESH_TOKEN, refreshToken);
		// 刷新网页授权凭证
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		if (null != jsonObject) {
			try {
				wat = getWeixinOauth2Token(jsonObject);
				response.success(wat);
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errorMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errorCode, errorMsg);
				log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return response;
	}

	/** 获取网页授权明细 */
	private static WeixinOauth2Token getWeixinOauth2Token(JSONObject jsonObject){
		WeixinOauth2Token wat = new WeixinOauth2Token();
		wat.setAccessToken(jsonObject.getString(Constants.ACCESS_TOKEN));
		wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
		wat.setRefreshToken(jsonObject.getString("refresh_token"));
		wat.setOpenId(jsonObject.getString("openid"));
		wat.setScope(jsonObject.getString("scope"));
		return wat;
	}
	
	/**
	 * 检验授权凭证（access_token）是否有效
	 * @param accessToken 网页授权的
	 * @param openId 用户标志（公众号）
	 * @return true/false
	 */
	public static Response checkOauth2AccessToken(String accessToken, String openId) {
		Response response = ResponseFactory.getInstance();
		String requestUrl = OAUTH2_CHECK_TOKEN_URL.replace(Constants.ACCESS_TOKEN, accessToken).replace(Constants.OPENID, openId);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errorMsg = jsonObject.getString(Constants.ERRMSG);
			if(errorCode == 0) {
				response.success();
			}else{
				response.fail(errorCode, errorMsg);
				log.error("检验授权凭证(access_token)是否有效失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return response;
	}
	
	/**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken 网页授权接口调用凭证
	 * @param openId 用户标识
	 * @return SNSUserInfo
	 */
	@SuppressWarnings( { "unchecked" })
	public static Response<SNSUserInfo> getSNSUserInfo(String accessToken, String openId) {
		Response response = ResponseFactory.getInstance();
		SNSUserInfo snsUserInfo;
		// 拼接请求地址
		String requestUrl = SNS_USERINFO_URL.replace(Constants.ACCESS_TOKEN, accessToken).replace(Constants.OPENID, openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);

		if (null != jsonObject) {
			try {
				snsUserInfo = new SNSUserInfo();
				// 用户的标识
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				snsUserInfo.setSex(jsonObject.getIntValue("sex"));
				// 用户所在国家
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				snsUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				snsUserInfo.setPrivilegeList(JSONArray.parseArray(jsonObject.getJSONArray("privilege").toJSONString(), String.class));
				// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
				if(jsonObject.containsKey("unionid")){
					snsUserInfo.setUnionid(jsonObject.getString("unionid"));
				}
				response.success(snsUserInfo);
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errorMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errorCode, errorMsg);
				log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return response;
	}
	
	/**
	 * 创建临时带参二维码
	 * 
	 * @param accessToken 接口访问凭证
	 * @param expireSeconds 二维码有效时间，单位为秒，最大不超过604800
	 * @param sceneId 场景ID
	 * @return WeixinQRCode
	 */
	@SuppressWarnings( { "unchecked" })
	public static Response<WeixinQRCode> createTemporaryQRCode(String accessToken, int expireSeconds, int sceneId) {
		Response response = ResponseFactory.getInstance();
		WeixinQRCode weixinQRCode;
		// 拼接请求地址
		String requestUrl = QRCODE_CREATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		String jsonMsg = "{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		//或者也可以使用以下POST数据创建字符串形式的二维码参数： TODO
		//{"expire_seconds": 604800, "action_name": "QR_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		// 创建临时带参二维码
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonMsg, expireSeconds, sceneId));

		if (null != jsonObject) {
			try {
				weixinQRCode = new WeixinQRCode();
				weixinQRCode.setTicket(jsonObject.getString("ticket"));
				weixinQRCode.setExpireSeconds(jsonObject.getIntValue("expire_seconds"));
				weixinQRCode.setUrl(jsonObject.getString("url"));
				response.success(weixinQRCode);
				log.info("创建临时带参二维码成功 ticket:{} expire_seconds:{}", weixinQRCode.getTicket(), weixinQRCode.getExpireSeconds());
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errorMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errorCode, errorMsg);
				log.error("创建临时带参二维码失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return response;
	}
	
	/**
	 * 创建永久带参二维码
	 * 
	 * @param accessToken 接口访问凭证
	 * @param sceneId 场景ID
	 * @return ticket
	 */
	@SuppressWarnings( { "unchecked" })
	public static Response<WeixinQRCode> createPermanentQRCode(String accessToken, int sceneId) {
		Response response = ResponseFactory.getInstance();
		WeixinQRCode weixinQRCode;
		// 拼接请求地址
		String requestUrl = QRCODE_CREATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		//或者也可以使用以下POST数据创建字符串形式的二维码参数：TODO
		//{"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		// 创建永久带参二维码
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonMsg, sceneId));

		if (null != jsonObject) {
			try {
				weixinQRCode = new WeixinQRCode();
				weixinQRCode.setTicket(jsonObject.getString("ticket"));
//				weixinQRCode.setExpireSeconds(jsonObject.getIntValue("expire_seconds"));
				weixinQRCode.setUrl(jsonObject.getString("url"));
				response.success(weixinQRCode);
				log.info("创建永久带参二维码成功 ticket:{}", weixinQRCode.getTicket());
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errorMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errorCode, errorMsg);
				log.error("创建永久带参二维码失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return response;
	}
	
	/**
	 * 根据ticket换取二维码
	 * TODO
	 * @param ticket 二维码ticket
	 * @param savePath 保存路径
	 */
	public static String getQRCode(String ticket, String savePath) {
		String filePath;
		// 拼接请求地址
		String requestUrl = QRCODE_GET_URL.replace(Constants.TICKET, CommonUtil.urlEncodeUTF8(ticket));
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod(Constants.HTTP_GET);

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}
			// 将ticket作为文件名
			filePath = savePath + ticket + ".jpg";

			// 将微信服务器返回的输入流写入文件
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
			fos.close();
			bis.close();

			conn.disconnect();
			log.info("根据ticket换取二维码成功，filePath=" + filePath);
		} catch (Exception e) {
			filePath = null;
			log.error("根据ticket换取二维码失败：{}", e);
		}
		return filePath;
	}
	
	/**
	 * 长链接转短链接
	 * @param accessToken 调用凭据
	 * @param longUrl 长链接
	 * @return 短链接
	 */
	@SuppressWarnings({"unchecked"})
	public static Response<String> longUrl2shortUrl(String accessToken, String longUrl) {
		Response response = ResponseFactory.getInstance();
		String shortUrl;
		String requestUrl = SHORTURL_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\"%s\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonMsg, longUrl));
		if(jsonObject != null){
			try{
				shortUrl = jsonObject.getString("short_url");
				response.success(shortUrl);
				log.info("长链接转短链接成功 shortUrl:{}", shortUrl);
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errorMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errorCode, errorMsg);
				log.error("长链接转短链接失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * 设置所属行业，设定两个行业，每月可修改行业1次
	 * @param accessToken 调用凭据
	 * @param industryId1 行业1
	 * @param industryId2 行业2
	 */
	public static void setIndustry(String accessToken, String industryId1, String industryId2) {
		String requestUrl = TEMPLATE_API_SET_INDUSTRY_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonMsg = "{\"industry_id1\":\"%s\",\"industry_id2\":\"%s\"}";
		CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonMsg, industryId1, industryId2));
	}
	
	/**
	 * TODO 解析成对象
	 * @param accessToken 调用凭据
	 * @return 已设置行业
	 */
	public static JSONObject getIndustry(String accessToken){
		String requestUrl = TEMPLATE_GET_INDUSTRY_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 发起GET请求查询菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		
		return jsonObj;
	}
	
	/**
	 * 获得模板ID
	 * @param accessToken 调用凭据
	 * @param templateIdshort 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return 模板ID
	 */
	@SuppressWarnings({"unchecked"})
	public static Response<String> addTemplateMsg(String accessToken, String templateIdshort) {
		Response response = ResponseFactory.getInstance();
		String templateId;
		String requestUrl = TEMPLATE_API_ADD_TEMPLATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonMsg = "{\"template_id_short\":\"%s\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonMsg, templateIdshort));
		if(jsonObject != null){
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if(errCode == 0){
				templateId = jsonObject.getString("template_id");
				response.success(templateId);
				log.info("增加模板消息成功 templateId:{}", templateId);
			} else {
				response.fail(errCode, errMsg);
				log.error("增加模板消息失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * TODO 解析成对象
	 * 获取模板列表
	 * @param accessToken 调用凭据
	 * @return 模板结果
	 */
	public static JSONObject getAllTempalte(String accessToken) {
		String requestUrl = TEMPLATE_GET_ALL_PRIVATE_TEMPLATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		
		return jsonObj;
	}
	
	/**
	 * 删除模板
	 * 
	 * @param accessToken 凭证
	 * @param templateId 模板id
	 * @return true/false
	 */
	public static Response deleteTemplate(String accessToken, String templateId) {
		Response response = ResponseFactory.getInstance();
		String requestUrl = TEMPLATE_DEL_PRIVATE_TEMPLATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonData = "{\"template_id\" : \"%s\" }";
		// 发起请求删除个性化菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, templateId));
		
		if(jsonObj != null){
			int errCode = jsonObj.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObj.getString(Constants.ERRMSG);
			if(errCode == 0){
				response.success();
				log.info("删除模板成功");
			} else {
				response.fail(errCode, errMsg);
				log.error("删除模板失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * TODO 解析成对象，入参jsonMsg
	 * 发送模板消息 
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return 消息id
	 */
	@SuppressWarnings({"unchecked"})
	public static Response<Long> sendTemplateMsg(String accessToken, String jsonMsg) {
		Response response = ResponseFactory.getInstance();
		Long msgid;
		String requestUrl = TEMPLATE_SEND_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if(errCode == 0){
				msgid = jsonObject.getLong("msgid");
				response.success(errCode, errMsg, msgid);
				log.info("发送模板消息成功 msgid:{}", msgid);
			} else {
				response.fail(errCode, errMsg);
				log.error("发送模板消息失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * TODO 解析成对象
	 * 获取公众号的自动回复规则
	 * 返回详细文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751299&token=&lang=zh_CN
	 * @param accessToken 调用凭据
	 * @return 结果
	 */
	public static JSONObject getAutoreplayInfo(String accessToken) {
		String requestUrl = MESSAGE_GET_CURRENT_AUTOREPLY_INFO.replace(Constants.ACCESS_TOKEN, accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		
		return jsonObj;
	}

}
