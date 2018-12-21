package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.model.UserTag;
import com.jd.union.wechat.api.model.UserTagMember;
import com.jd.union.wechat.api.model.WeixinUserInfo;
import com.jd.union.wechat.api.model.WeixinUserList;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户管理
 * @author zhangjianhui
 *
 */
@Slf4j
public class UserUtil {
	
	/**
	 * 创建标签
	 * 上限100
	 * @param accessToken 接口访问凭证
	 * @param tagName 标签名称
	 * @return 用户标志
	 */
	public static UserTag createTag(String accessToken, String tagName) {
		UserTag userTag = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"tag\" : {\"name\" : \"%s\"}}";
		// 创建分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, tagName));

		if (null != jsonObject) {
			try {
				userTag = new UserTag();
				userTag.setId(jsonObject.getJSONObject("tag").getIntValue("id"));
				userTag.setName(jsonObject.getJSONObject("tag").getString("name"));
			} catch (JSONException e) {
				userTag = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("创建标签失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return userTag;
	}
	
	/**
	 * 获取公众号已创建的标签
	 * 
	 * @param accessToken 调用接口凭证
	 */
	@SuppressWarnings( { "unchecked" })
	public static List<UserTag> getTags(String accessToken) {
		List<UserTag> userTagList = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			try {
				userTagList = JSONArray.parseArray(jsonObject.getJSONArray("tags").toJSONString(), UserTag.class);
			} catch (JSONException e) {
				userTagList = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("查询标签失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return userTagList;
	}
	
	/**
	 * 编辑标签
	 * 
	 * @param accessToken 接口访问凭证
	 * @param tagId 标签id
	 * @param tagName 修改后的标签
	 * @return true | false
	 */
	public static boolean updateTag(String accessToken, int tagId, String tagName) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"tag\": {\"id\": %d, \"name\": \"%s\"}}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, tagId, tagName));
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("编辑标签成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("编辑标签失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}
	
	/**
	 * 删除标签
	 * 当某个标签下的粉丝超过10w时，后台不可直接删除标签。此时，开发者可以对该标签下的openid列表，先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
	 * @param accessToken 接口访问凭证
	 * @param tagId 标签id
	 * @return true | false
	 */
	public static boolean deleteTag(String accessToken, int tagId) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"tag\":{\"id\":%d}}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, tagId));
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("删除标签成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("删除标签失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 获取标签下粉丝列表
	 * @param accessToken 调用接口凭证
	 * @param tagId 标签ID
	 * @param nextOpenId 第一个拉取的openId，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings( { "unchecked" })
	public static WeixinUserList getTag(String accessToken, int tagId, String nextOpenId) {
		WeixinUserList weixinUserList = null;

		nextOpenId = StringUtils.isBlank(nextOpenId) ? "" : nextOpenId;

		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonData = "{\"tagid\": %d, \"next_openid\": \"%s\"}";
		
		// 获取关注者列表
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, tagId, nextOpenId));
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				//weixinUserList.setTotal(jsonObject.getIntValue("total"));
				weixinUserList.setCount(jsonObject.getIntValue("count"));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.parseArray(dataObject.getJSONArray("openid").toJSONString(), String.class));
			} catch (JSONException e) {
				weixinUserList = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取标签下粉丝列表失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinUserList;
	}
	
	/**
	 * 批量为用户打标签
	 * 标签功能目前支持公众号为用户打上最多三个标签
	 * @param accessToken 接口访问凭证
	 * @param tagId 标签id
	 * @return true | false
	 */
	public static boolean batchTagging(String accessToken, int tagId, List<String> openid_list) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		UserTagMember userTagMember = new UserTagMember(tagId, openid_list);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", JSONObject.toJSONString(userTagMember));
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("批量为用户打标签成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("批量为用户打标签失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 批量为用户取消标签
	 * 标签功能目前支持公众号为用户打上最多三个标签
	 * @param accessToken 接口访问凭证
	 * @param tagId 标签id
	 * @return true | false
	 */
	public static boolean batchUntagging(String accessToken, int tagId, List<String> openid_list) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		UserTagMember userTagMember = new UserTagMember(tagId, openid_list);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", JSONObject.toJSONString(userTagMember));
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("批量为用户取消标签成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("批量为用户取消标签失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 获取用户身上的标签列表
	 * 
	 * @param accessToken 调用接口凭证
	 * @param openid 用户标志(公众号)
	 */
	@SuppressWarnings( { "unchecked" })
	public static List<Integer> getTagidList(String accessToken, String openid) {
		List<Integer> userTagList = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonData = "{\"openid\": \"%s\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, openid));
		if (null != jsonObject) {
			try {
				userTagList = JSONArray.parseArray(jsonObject.getJSONArray("tagid_list").toJSONString(), Integer.class);
			} catch (JSONException e) {
				userTagList = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户身上的标签列表 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return userTagList;
	}
	
	/**
	 * 设置用户备注名
	 * @param accessToken 接口访问凭证
	 * @param openId 用户标识 
	 * @param remark 新的备注名，长度必须小于30字符
	 * @return true/false
	 */
	public static boolean setUserRemarkName(String accessToken, String openId, String remark) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"openid\":\"%s\",\"remark\":\"%s\"}";
		// 移动用户分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, openId, remark));

		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("设置用户备注名成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("设置用户备注名失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return result;
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @param accessToken 接口访问凭证
	 * @param openId 用户标识
	 * @param lang 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return WeixinUserInfo
	 */
	@SuppressWarnings("unchecked")
	public static WeixinUserInfo getUserInfo(String accessToken, String openId, String lang) {
		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId).replace("zh_CN", lang==null?"":lang);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenid(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getIntValue("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribe_time(jsonObject.getLong("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getIntValue("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
				// 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
				if(jsonObject.containsKey("unionid")){
					weixinUserInfo.setUnionid(jsonObject.getString("unionid"));
				}
				// 公众号运营者对粉丝的备注
				weixinUserInfo.setRemark(jsonObject.getString("remark"));
				// 用户所在的分组ID
				weixinUserInfo.setGroupid(jsonObject.getIntValue("groupid"));
				// 用户被打上的标签ID列表
				weixinUserInfo.setTagid_list(JSONArray.parseArray(jsonObject.getJSONArray("tagid_list").toJSONString(), Integer.class));
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("用户{}已取消关注", weixinUserInfo.getOpenid());
				} else {
					int errorCode = jsonObject.getIntValue("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				}
			}
		}
		return weixinUserInfo;
	}
	
	/**
	 * 批量获取用户信息（最多一次100）
	 * 
	 * @param accessToken 接口访问凭证
	 * @param openIdLis 用户标识列表
	 * @param lang 国家地区语言 zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
	 * @return List<WeixinUserInfo>
	 */
	@SuppressWarnings("unchecked")
	public static List<WeixinUserInfo> getUserInfoBatch(String accessToken, List<String> openIdLis, String lang) {
		List<WeixinUserInfo> wuiLis = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 封装参数
		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
		List<Map<String, String>> openIdmaplis = new ArrayList<Map<String, String>>();
		Map<String, String> openIdMap;
		for(String openId : openIdLis){
			openIdMap = new HashMap<String, String>();
			openIdMap.put("openid", openId);
			openIdMap.put("lang", lang);
			openIdmaplis.add(openIdMap);
		}
		map.put("user_list", openIdmaplis);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", JSONObject.toJSONString(map));
		if (null != jsonObject) {
			try {
				wuiLis = JSONArray.parseArray(jsonObject.getJSONArray("user_info_list").toJSONString(), WeixinUserInfo.class);
			} catch (Exception e) {
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户信息(批量)失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return wuiLis;
	}
	
	/**
	 * 获取关注者列表
	 * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求
	 * @param accessToken 调用接口凭证
	 * @param nextOpenId 第一个拉取的openId，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings( { "unchecked" })
	public static WeixinUserList getUserList(String accessToken, String nextOpenId) {
		WeixinUserList weixinUserList = null;

		if (null == nextOpenId)
			nextOpenId = "";

		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);
		// 获取关注者列表
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				weixinUserList.setTotal(jsonObject.getIntValue("total"));
				weixinUserList.setCount(jsonObject.getIntValue("count"));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.parseArray(dataObject.getJSONArray("openid").toJSONString(), String.class));
			} catch (JSONException e) {
				weixinUserList = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取关注者列表失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinUserList;
	}
	
	/**
	 * 获取公众号的黑名单列表
	 * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求
	 * @param accessToken 调用接口凭证
	 * @param begin_openid 第一个拉取的openId，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings( { "unchecked" })
	public static WeixinUserList getBlackList(String accessToken, String begin_openid) {
		WeixinUserList weixinUserList = null;

		begin_openid = StringUtils.isBlank(begin_openid) ? "" : begin_openid;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", begin_openid);
		String jsonData = "{\"begin_openid\": \"%s\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonData, begin_openid));
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				weixinUserList.setTotal(jsonObject.getIntValue("total"));
				weixinUserList.setCount(jsonObject.getIntValue("count"));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.parseArray(dataObject.getJSONArray("openid").toJSONString(), String.class));
			} catch (JSONException e) {
				weixinUserList = null;
				int errorCode = jsonObject.getIntValue("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取公众号的黑名单列表失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return weixinUserList;
	}
	
	/**
	 * 拉黑用户
	 * 公众号可通过该接口来拉黑一批用户
	 * @param accessToken 接口访问凭证
	 * @return true | false
	 */
	public static boolean batchBlackList(String accessToken, List<String> openid_list) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		UserTagMember userTagMember = new UserTagMember(0, openid_list);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", JSONObject.toJSONString(userTagMember));
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("批量拉黑用户成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("批量拉黑用户失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
	
	/**
	 * 取消拉黑用户
	 * 公众号可通过该接口来拉黑一批用户
	 * @param accessToken 接口访问凭证
	 * @return true | false
	 */
	public static boolean batchUnblackList(String accessToken, List<String> openid_list) {
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		UserTagMember userTagMember = new UserTagMember(0, openid_list);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", JSONObject.toJSONString(userTagMember));
		if (null != jsonObject) {
			int errorCode = jsonObject.getIntValue("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
				log.info("批量取消拉黑用户成功 errcode:{} errmsg:{}", errorCode, errorMsg);
			} else {
				log.error("批量取消拉黑用户失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		
		return result;
	}
}
