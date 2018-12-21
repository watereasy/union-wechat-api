package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.message.resp.Article;
import com.jd.union.wechat.api.message.resp.Music;
import com.jd.union.wechat.api.model.CustomAccount;
import com.jd.union.wechat.api.model.MassResult;
import com.jd.union.wechat.api.model.SNSUserInfo;
import com.jd.union.wechat.api.model.WeixinGroup;
import com.jd.union.wechat.api.model.WeixinMedia;
import com.jd.union.wechat.api.model.WeixinOauth2Token;
import com.jd.union.wechat.api.model.WeixinQRCode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import lombok.extern.slf4j.Slf4j;

/**
 * 高级接口工具类
 *
 */
@Slf4j
public class AdvancedUtil {
	
	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param appSecret 公众账号的密钥
	 * @param code 请求code
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String appId, String appSecret, String code) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}"+ errorCode+","+ errorMsg);
			}
		}
		return wat;
	}
	
	/**
	 * 刷新网页授权凭证
	 * 
	 * @param appId 公众账号的唯一标识
	 * @param refreshToken 刷新token
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token refreshOauth2AccessToken(String appId, String refreshToken) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("REFRESH_TOKEN", refreshToken);
		// 刷新网页授权凭证
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getIntValue("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("刷新网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wat;
	}
	
	/**
	 * 检验授权凭证（access_token）是否有效
	 * @param accessToken 网页授权的
	 * @param openId 用户标志（公众号）
	 * @return true/false
	 */
	public static boolean checkOauth2AccessToken(String accessToken, String openId) {
		boolean result = false;
		String requestUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if(errorCode == 0) {
				result = true;
				log.info("检验授权凭证(access_token)是否有效成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			}else{
				log.error("检验授权凭证(access_token)是否有效失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}
	
	/**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken 网页授权接口调用凭证
	 * @param openId 用户标识
	 * @return SNSUserInfo
	 */
	@SuppressWarnings( { "unchecked" })
	public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) {
		SNSUserInfo snsUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 通过网页授权获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

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
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return snsUserInfo;
	}
	
	/**
	 * 创建临时带参二维码
	 * 
	 * @param accessToken 接口访问凭证
	 * @param expireSeconds 二维码有效时间，单位为秒，最大不超过604800
	 * @param sceneId 场景ID
	 * @return WeixinQRCode
	 */
	public static WeixinQRCode createTemporaryQRCode(String accessToken, int expireSeconds, int sceneId) {
		WeixinQRCode weixinQRCode = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonMsg = "{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		// 创建临时带参二维码
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, expireSeconds, sceneId));

		if (null != jsonObject) {
			try {
				weixinQRCode = new WeixinQRCode();
				weixinQRCode.setTicket(jsonObject.getString("ticket"));
				weixinQRCode.setExpireSeconds(jsonObject.getIntValue("expire_seconds"));
				weixinQRCode.setUrl(jsonObject.getString("url"));
				log.info("创建临时带参二维码成功 ticket:{} expire_seconds:{}", weixinQRCode.getTicket(), weixinQRCode.getExpireSeconds());
			} catch (Exception e) {
				weixinQRCode = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("创建临时带参二维码失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinQRCode;
	}
	
	/**
	 * 创建永久带参二维码
	 * 
	 * @param accessToken 接口访问凭证
	 * @param sceneId 场景ID
	 * @return ticket
	 */
	public static String createPermanentQRCode(String accessToken, int sceneId) {
		String ticket = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		// 创建永久带参二维码
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, sceneId));

		if (null != jsonObject) {
			try {
				ticket = jsonObject.getString("ticket");
				log.info("创建永久带参二维码成功 ticket:{}", ticket);
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("创建永久带参二维码失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return ticket;
	}
	
	/**
	 * 根据ticket换取二维码
	 * 
	 * @param ticket 二维码ticket
	 * @param savePath 保存路径
	 */
	public static String getQRCode(String ticket, String savePath) {
		String filePath;
		// 拼接请求地址
		String requestUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		requestUrl = requestUrl.replace("TICKET", CommonUtil.urlEncodeUTF8(ticket));
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

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
	public static String longUrl2shortUrl(String accessToken, String longUrl) {
		String shortUrl = "";
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"action\":\"long2short\",\"long_url\":\"%s\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, longUrl));
		if(jsonObject != null){
			try{
				shortUrl = jsonObject.getString("short_url");
				log.info("长链接转短链接成功 shortUrl:{}", shortUrl);
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("长链接转短链接失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return shortUrl;
	}
	
	/**
	 * 查询分组
	 * 
	 * @param accessToken 调用接口凭证
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public static List<WeixinGroup> getGroups(String accessToken) {
		List<WeixinGroup> weixinGroupList = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 查询分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinGroupList = JSONArray.parseArray(jsonObject.getJSONArray("groups").toJSONString(),WeixinGroup.class);
			} catch (JSONException e) {
				weixinGroupList = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("查询分组失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinGroupList;
	}
	
	/**
	 * 查询用户所在分组
	 * @param accessToken 调用凭据
	 * @param openID 用户标志（公众号）
	 * @return 分组标志
	 */
	public static int queryUserGroup(String accessToken, String openID) {
		int groupID = -1;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonData = "{\"openid\" : \"%s\" }";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, openID));
		if (null != jsonObject) {
			if(jsonObject.containsKey("groupid")) {
				groupID = jsonObject.getIntValue("groupid");
			}else{
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("查询分组失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return groupID;
	}
	
	/**
	 * 创建分组
	 * 上限100
	 * @param accessToken 接口访问凭证
	 * @param groupName 分组名称
	 * @return 分组明细
	 */
	@SuppressWarnings("deprecation")
	public static WeixinGroup createGroup(String accessToken, String groupName) {
		WeixinGroup weixinGroup = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"group\" : {\"name\" : \"%s\"}}";
		// 创建分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, groupName));

		if (null != jsonObject) {
			try {
				weixinGroup = new WeixinGroup();
				weixinGroup.setId(jsonObject.getJSONObject("group").getIntValue("id"));
				weixinGroup.setName(jsonObject.getJSONObject("group").getString("name"));
			} catch (JSONException e) {
				weixinGroup = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("创建分组失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinGroup;
	}
	
	/**
	 * 修改分组名
	 * 
	 * @param accessToken 接口访问凭证
	 * @param groupId 分组id
	 * @param groupName 修改后的分组名
	 * @return true | false
	 */
	public static boolean updateGroup(String accessToken, int groupId, String groupName) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"group\": {\"id\": %d, \"name\": \"%s\"}}";
		// 修改分组名
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, groupId, groupName));

		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("修改分组名成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("修改分组名失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}
	
	/**
	 * 移动用户分组
	 * 
	 * @param accessToken 接口访问凭证
	 * @param openId 用户标识
	 * @param groupId 分组id
	 * @return true | false
	 */
	public static boolean updateMemberGroup(String accessToken, String openId, int groupId) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";
		// 移动用户分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, openId, groupId));

		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("移动用户分组成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("移动用户分组失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}
	
	/**
	 * 批量移动用户分组
	 * 
	 * @param accessToken 接口访问凭证
	 * @param openIdLis 用户标识集合
	 * @param groupId 分组id
	 * @return true|false
	 */
	public static boolean updateMembersGroup(String accessToken, List<String> openIdLis, int groupId){
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"openid_list\":%s,\"to_groupid\":%d}";
		// 批量移动用户分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, JSONArray.toJSONString(openIdLis), groupId));

		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("批量移动用户分组成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("批量移动用户分组失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * TODO 请求成功jsonObject返回是{}，请微信官方回答
	 * 删除分组
	 * 删除一个用户分组，删除分组后，所有该分组内的用户自动进入默认分组
	 * @param accessToken 接口访问凭证
	 * @param groupId 分组id
	 * @return true | false
	 */
	public static boolean deleteGroup(String accessToken, int groupId) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"group\":{\"id\":%d}}";
		// 移动用户分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, groupId));
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("删除分组成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("删除分组失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	
	
	/**
	 * 上传图文消息素材【订阅号与服务号认证后均可用】 
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return 上传后素材明细
	 */
	public static WeixinMedia uploadNews(String accessToken, String jsonMsg) {
		WeixinMedia weixinMedia = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			if (jsonObject.containsKey("media_id ")) {
				weixinMedia = new WeixinMedia();
				weixinMedia.setType(jsonObject.getString("type"));
				weixinMedia.setMediaId(jsonObject.getString("media_id"));
				weixinMedia.setCreatedAt(jsonObject.getLong("created_at"));
				log.info("上传图文消息素材成功 errcode:{} errmsg:{}");
			} else {
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("上传图文消息素材失败 errcode:{} errmsg:{}", errorCode,
						errorMsg);
			}
		}

		return weixinMedia;
	}
	
	/**
	 * 根据分组群发消息【订阅号与服务号认证后均可用】
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return 群发结果
	 */
	public static MassResult massMsgByGroup(String accessToken, String jsonMsg) {
		MassResult mr = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue("errcode");
			String errMsg = jsonObject.getString("errmsg");
			mr = new MassResult();
			mr.setErrCode(errCode);
			mr.setErrMsg(errMsg);
			if (errCode == 0) {
				int msgId = jsonObject.getIntValue("msg_id");
				mr.setMsgId(msgId);
				log.info("分组群发图文消息成功 msg_id:{} ", msgId);
			} else {
				log.error("分组群发图文消息失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return mr;
	}
	
	/**
	 * 根据OpenID的群发消息【订阅号不可用，服务号认证后可用】
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return 群发结果
	 */
	public static MassResult massMsgByOpenID(String accessToken, String jsonMsg) {
		MassResult mr = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue("errcode");
			String errMsg = jsonObject.getString("errmsg");
			mr = new MassResult();
			mr.setErrCode(errCode);
			mr.setErrMsg(errMsg);
			if (errCode == 0) {
				int msgId = jsonObject.getIntValue("msg_id");
				mr.setMsgId(msgId);
				log.info("OpenID群发图文消息成功 msgId:{}", msgId);
			} else {
				log.error("OpenID群发图文消息失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return mr;
	}
	
	/**
	 * 删除群发【订阅号与服务号认证后均可用】
	 * 已经发送成功的消息才能删除,删除消息只是将消息的图文详情页失效,已经收到的用户，还是能在其本地看到消息卡片。
	 * 另外，删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return 删除群发结果
	 */
	public static boolean deleteMassMsg(String accessToken, String jsonMsg) {
		boolean result = false;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (errorCode == 0) {
				result = true;
				log.info("群发图文消息成功 errcode:{} errmsg:{}");
			} else {
				log.error("群发图文消息失败 errcode:{} errmsg:{}", errorCode,
						errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 群发预览接口【订阅号与服务号认证后均可用】 
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return 群发预览
	 */
	public static MassResult massMsgPreview(String accessToken, String jsonMsg) {
		MassResult mr = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue("errcode");
			String errMsg = jsonObject.getString("errmsg");
			mr = new MassResult();
			mr.setErrCode(errCode);
			mr.setErrMsg(errMsg);
			if (errCode == 0) {
				// TODO 并没有msg_id，是不是接口返回有问题(与文档说明不一致)
				//int msgId = jsonObject.getIntValue("msg_id");
				//mr.setMsgId(msgId);
				log.info("预览群发图文消息成功");
			} else {
				log.error("预览群发图文消息失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return mr;
	}
	
	/**
	 * 查询群发消息发送状态【订阅号与服务号认证后均可用】 
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return 群发消息结果
	 */
	public static MassResult queryMassMsgStatus(String accessToken, String jsonMsg) {
		MassResult mr = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			int msgId  = jsonObject.getIntValue("msg_id");
			String msgStatus = jsonObject.getString("msg_status");
			mr = new MassResult();
			mr.setMsgId(msgId);
			mr.setMsgStatus(msgStatus);
			if ("SEND_SUCCESS".equals(msgStatus)) {
				log.info("查询群发消息结果成功 msgId:{}", msgId);
			} else {
				log.error("查询群发消息结果失败 msgId:{} msgStatus:{}", msgId,
						msgStatus);
			}
		}
		
		return mr;
	}
	
	/**
	 * 获取群发视频的特殊接口
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return
	 * @deprecated (见素材管理)
	 */
	@Deprecated
	public static WeixinMedia getMassVideo(String accessToken, String jsonMsg) {
		WeixinMedia weixinMedia = null;
		String requestUrl = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if (null != jsonObject) {
			if (jsonObject.containsKey("media_id ")) {
				weixinMedia = new WeixinMedia();
				weixinMedia.setType(jsonObject.getString("type"));
				weixinMedia.setMediaId(jsonObject.getString("media_id"));
				weixinMedia.setCreatedAt(jsonObject.getLong("created_at"));
				log.info("获取群发视频成功 errcode:{} errmsg:{}");
			} else {
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取群发视频失败 errcode:{} errmsg:{}", errorCode,
						errorMsg);
			}
		}
		
		return weixinMedia;
	}
	
	/**
	 * 设置所属行业，设定两个行业，每月可修改行业1次
	 * @param accessToken 调用凭据
	 * @param industryId1 行业1
	 * @param industryId2 行业2
	 */
	public static void setIndustry(String accessToken, String industryId1, String industryId2) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
		String jsonMsg = "{\"industry_id1\":\"%s\",\"industry_id2\":\"%s\"}";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, industryId1, industryId2));
	}
	
	/**
	 * @param accessToken 调用凭据
	 * @return 已设置行业
	 */
	public static JSONObject getIndustry(String accessToken){
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 发起GET请求查询菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		return jsonObj;
	}
	
	/**
	 * 获得模板ID
	 * @param accessToken 调用凭据
	 * @param templateIdshort 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
	 * @return 模板ID
	 */
	public static String addTemplateMsg(String accessToken, String templateIdshort) {
		String templateId = "";
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"template_id_short\":\"%s\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, templateIdshort));
		if(jsonObject != null){
			int errCode = jsonObject.getIntValue("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				templateId = jsonObject.getString("template_id");
				log.info("增加模板消息成功 templateId:{}", templateId);
			} else {
				log.error("增加模板消息失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return templateId;
	}
	
	/**
	 * 获取模板列表
	 * @param accessToken 调用凭据
	 * @return 模板结果
	 */
	public static JSONObject getAllTempalte(String accessToken) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		return jsonObj;
	}
	
	/**
	 * 删除模板
	 * 
	 * @param accessToken 凭证
	 * @param templateId 模板id
	 * @return true/false
	 */
	public static boolean deleteTempalte(String accessToken, String templateId) {
		boolean result = false;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonData = "{\"template_id\" : \"%s\" }";
		// 发起请求删除个性化菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, templateId));
		
		if(jsonObj != null){
			int errorCode = jsonObj.getIntValue("errcode");
			String errorMsg = jsonObj.getString("errmsg");
			if(errorCode == 0){
				result = true;
				log.info("删除模板成功");
			} else {
				log.error("删除模板失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 发送模板消息 
	 * @param accessToken 调用凭据
	 * @param jsonMsg 消息体
	 * @return 消息id
	 */
	public static String sendTemplateMsg(String accessToken, String jsonMsg) {
		String msgid = "";
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getIntValue("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				msgid = jsonObject.getString("msgid");
				log.info("发送模板消息成功 msgid:{}", msgid);
			} else {
				log.error("发送模板消息失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return msgid;
	}
	
	/**
	 * 获取公众号的自动回复规则
	 * 返回详细文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751299&token=&lang=zh_CN
	 * @param accessToken 调用凭据
	 * @return 结果
	 */
	public static JSONObject getAutoreplayInfo(String accessToken) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, "GET", null);
		
		return jsonObj;
	}

}
