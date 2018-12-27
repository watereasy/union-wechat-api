package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.factory.ResponseFactory;
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

	/** 创建标签(POST) */
	private final static String TAGS_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN";
	/** 获取公众号已创建的标签(GET) */
	private final static String TAGS_GET_URL = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN";
	/** 编辑标签(POST) */
	private final static String TAGS_UPDATE_URL = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN";
	/** 删除标签(POST) */
	private final static String TAGS_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN";
	/** 获取标签下粉丝列表(GET) */
	private final static String USER_TAG_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN";
	/** 获取标签下粉丝列表(POST) */
	private final static String TAGS_MEMBERS_BATCHTAGGING_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN";
	/** 批量为用户取消标签(POST) */
	private final static String TAGS_MEMBERS_BATCHUNTAGGING_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN";
	/** 获取用户身上的标签列表(POST) */
	private final static String TAGS_GETIDLIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN";
	/** 设置用户备注名(POST) */
	private final static String USER_INFO_UPDATEREMARK_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
	/** 获取用户基本信息(UnionID机制)(GET) */
	private final static String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	/** 批量获取用户基本信息(POST) */
	private final static String USER_INFO_BATCHGET_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";
	/** 获取用户列表-关注(GET) */
	private final static String USER_GET_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	/** 获取公众号的黑名单列表(POST) */
	private final static String TAGS_MEMBERS_GETBLACKLIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/getblacklist?access_token=ACCESS_TOKEN";
	/** 拉黑用户(POST) */
	private final static String TAGS_MEMBERS_BATCHBLACKLIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchblacklist?access_token=ACCESS_TOKEN";
	/** 取消拉黑用户(POST) */
	private final static String TAGS_MEMBERS_BATCHUNBLACKLIST_URL = "https://api.weixin.qq.com/cgi-bin/tags/members/batchunblacklist?access_token=ACCESS_TOKEN";

	/**
	 * 创建标签
	 * 上限100
	 * @param accessToken 接口访问凭证
	 * @param tagName 标签名称
	 * @return 用户标志
	 */
	@SuppressWarnings({"unchecked"})
	public static Response<UserTag> createTag(String accessToken, String tagName) {
		Response response = ResponseFactory.getInstance();
		UserTag userTag;
		// 拼接请求地址
		String requestUrl = TAGS_CREATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		String jsonData = "{\"tag\" : {\"name\" : \"%s\"}}";
		// 创建分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, tagName));
		if (null != jsonObject) {
			try {
				userTag = new UserTag();
				userTag.setId(jsonObject.getJSONObject("tag").getIntValue("id"));
				userTag.setName(jsonObject.getJSONObject("tag").getString("name"));
				response.success(userTag);
			} catch (JSONException e) {
				int errCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errCode, errMsg);
				log.error("创建标签失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
	}
	
	/**
	 * 获取公众号已创建的标签
	 * 
	 * @param accessToken 调用接口凭证
	 */
	@SuppressWarnings( { "unchecked" })
	public static Response<List<UserTag>> getTags(String accessToken) {
		Response response = ResponseFactory.getInstance();
		List<UserTag> userTagList;
		// 拼接请求地址
		String requestUrl = TAGS_GET_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		if (null != jsonObject) {
			try {
				userTagList = JSONArray.parseArray(jsonObject.getJSONArray("tags").toJSONString(), UserTag.class);
				response.success(userTagList);
			} catch (JSONException e) {
				int errCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errCode, errMsg);
				log.error("查询标签失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
	}
	
	/**
	 * 编辑标签
	 * 
	 * @param accessToken 接口访问凭证
	 * @param tagId 标签id
	 * @param tagName 修改后的标签
	 * @return true | false
	 */
	public static Response updateTag(String accessToken, int tagId, String tagName) {
		Response response = ResponseFactory.getInstance();
		// 拼接请求地址
		String requestUrl = TAGS_UPDATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		String jsonData = "{\"tag\": {\"id\": %d, \"name\": \"%s\"}}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, tagId, tagName));
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if (0 == errCode) {
				response.success();
				log.info("编辑标签成功 errcode:{} errmsg:{}", errCode, errMsg);
			} else {
				response.fail(errCode, errMsg);
				log.error("编辑标签失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
	}
	
	/**
	 * 删除标签
	 * 当某个标签下的粉丝超过10w时，后台不可直接删除标签。此时，开发者可以对该标签下的openid列表，先进行取消标签的操作，直到粉丝数不超过10w后，才可直接删除该标签。
	 * @param accessToken 接口访问凭证
	 * @param tagId 标签id
	 * @return true | false
	 */
	public static Response deleteTag(String accessToken, int tagId) {
		Response response = ResponseFactory.getInstance();
		// 拼接请求地址
		String requestUrl = TAGS_DELETE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		String jsonData = "{\"tag\":{\"id\":%d}}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, tagId));
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if (0 == errCode) {
				response.success();
				log.info("删除标签成功 errcode:{} errmsg:{}", errCode, errMsg);
			} else {
				response.fail(errCode, errMsg);
				log.error("删除标签失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * 获取标签下粉丝列表
	 * @param accessToken 调用接口凭证
	 * @param tagId 标签ID
	 * @param nextOpenId 第一个拉取的openId，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings( { "unchecked" })
	public static Response<WeixinUserList> getTag(String accessToken, int tagId, String nextOpenId) {
		Response response = ResponseFactory.getInstance();
		WeixinUserList weixinUserList;
		nextOpenId = StringUtils.isBlank(nextOpenId) ? "" : nextOpenId;
		// 拼接请求地址
		String requestUrl = USER_TAG_GET_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonData = "{\"tagid\": %d, \"next_openid\": \"%s\"}";
		// 获取关注者列表
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, tagId, nextOpenId));
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				weixinUserList.setCount(jsonObject.getLong("count"));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.parseArray(dataObject.getJSONArray("openid").toJSONString(), String.class));
				response.success(weixinUserList);
			} catch (JSONException e) {
				int errCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errCode, errMsg);
				log.error("获取标签下粉丝列表失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
	}

	/**
	 * 批量为用户打标签
	 * 标签功能目前支持公众号为用户打上最多三个标签
	 * @param accessToken 接口访问凭证
	 * @param tagId 标签id
	 * @return true | false
	 */
	public static Response batchTagging(String accessToken, int tagId, List<String> openid_list) {
		Response response = ResponseFactory.getInstance();
		// 拼接请求地址
		String requestUrl = TAGS_MEMBERS_BATCHTAGGING_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		UserTagMember userTagMember = new UserTagMember(tagId, openid_list);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, JSONObject.toJSONString(userTagMember));
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if (0 == errCode) {
				response.success();
				log.info("批量为用户打标签成功 errcode:{} errmsg:{}", errCode, errMsg);
			} else {
				response.fail(errCode, errMsg);
				log.error("批量为用户打标签失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}

		return response;
	}

	/**
	 * 批量为用户取消标签
	 * 标签功能目前支持公众号为用户打上最多三个标签
	 * @param accessToken 接口访问凭证
	 * @param tagId 标签id
	 * @return true | false
	 */
	public static Response batchUntagging(String accessToken, int tagId, List<String> openid_list) {
		Response response = ResponseFactory.getInstance();
		// 拼接请求地址
		String requestUrl = TAGS_MEMBERS_BATCHUNTAGGING_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		UserTagMember userTagMember = new UserTagMember(tagId, openid_list);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, JSONObject.toJSONString(userTagMember));
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if (0 == errCode) {
				response.success();
				log.info("批量为用户取消标签成功 errcode:{} errmsg:{}", errCode, errMsg);
			} else {
				response.fail(errCode, errMsg);
				log.error("批量为用户取消标签失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}

		return response;
	}

	/**
	 * 获取用户身上的标签列表
	 *
	 * @param accessToken 调用接口凭证
	 * @param openid 用户标志(公众号)
	 */
	@SuppressWarnings( { "unchecked" })
	public static Response<List<Integer>> getTagidList(String accessToken, String openid) {
		Response response = ResponseFactory.getInstance();
		List<Integer> userTagList;
		// 拼接请求地址
		String requestUrl = TAGS_GETIDLIST_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonData = "{\"openid\": \"%s\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, openid));
		if (null != jsonObject) {
			try {
				userTagList = JSONArray.parseArray(jsonObject.getJSONArray("tagid_list").toJSONString(), Integer.class);
				response.success(userTagList);
			} catch (JSONException e) {
				int errCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errCode, errMsg);
				log.error("获取用户身上的标签列表 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
	}

	/**
	 * 设置用户备注名
	 * @param accessToken 接口访问凭证
	 * @param openId 用户标识
	 * @param remark 新的备注名，长度必须小于30字符
	 * @return true/false
	 */
	public static Response setUserRemarkName(String accessToken, String openId, String remark) {
		Response response = ResponseFactory.getInstance();
		// 拼接请求地址
		String requestUrl = USER_INFO_UPDATEREMARK_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		String jsonData = "{\"openid\":\"%s\",\"remark\":\"%s\"}";
		// 移动用户分组
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, openId, remark));
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if (0 == errCode) {
				response.success();
				log.info("设置用户备注名成功 errcode:{} errmsg:{}", errCode, errMsg);
			} else {
				response.fail(errCode, errMsg);
				log.error("设置用户备注名失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
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
	public static Response<WeixinUserInfo> getUserInfo(String accessToken, String openId, String lang) {
		Response response = ResponseFactory.getInstance();
		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = USER_INFO_URL.replace(Constants.ACCESS_TOKEN, accessToken).replace(Constants.OPENID, openId).replace("zh_CN", lang==null?"":lang);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenid(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getInteger("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribe_time(jsonObject.getLong("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInteger("sex"));
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
				weixinUserInfo.setGroupid(jsonObject.getInteger("groupid"));
				// 用户被打上的标签ID列表
				weixinUserInfo.setTagid_list(JSONArray.parseArray(jsonObject.getJSONArray("tagid_list").toJSONString(), Integer.class));
				// 返回用户关注的渠道来源
				weixinUserInfo.setSubscribe_scene(jsonObject.getString("subscribe_scene"));
				// 二维码扫码场景（开发者自定义）
				weixinUserInfo.setQr_scene(jsonObject.getString("qr_scene"));
				// 二维码扫码场景描述（开发者自定义）
				weixinUserInfo.setQr_scene_str(jsonObject.getString("qr_scene_str"));
				response.success(weixinUserInfo);
			} catch (Exception e) {
				if (weixinUserInfo.getSubscribe() == null || 0 == weixinUserInfo.getSubscribe()) {
					log.error("用户{}已取消关注", weixinUserInfo.getOpenid());
				} else {
					int errCode = jsonObject.getIntValue(Constants.ERRCODE);
					String errMsg = jsonObject.getString(Constants.ERRMSG);
					response.fail(errCode, errMsg);
					log.error("获取用户信息失败 errcode:{} errmsg:{}", errCode, errMsg);
				}
			}
		}
		return response;
	}

	/**
	 * 批量获取用户信息（最多一次100）
	 *
	 * @param accessToken 接口访问凭证
	 * @param openIdLis 用户标识列表
	 * @param lang 国家地区语言 zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
	 * @return Response<List<WeixinUserInfo>>
	 */
	@SuppressWarnings("unchecked")
	public static Response<List<WeixinUserInfo>> getUserInfoBatch(String accessToken, List<String> openIdLis, String lang) {
		Response response = ResponseFactory.getInstance();
		List<WeixinUserInfo> wuiLis;
		// 拼接请求地址
		String requestUrl = USER_INFO_BATCHGET_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 封装参数
		Map<String, List<Map<String, String>>> map = new HashMap<>();
		List<Map<String, String>> openIdmaplis = new ArrayList<>();
		Map<String, String> openIdMap;
		for(String openId : openIdLis){
			openIdMap = new HashMap<>();
			openIdMap.put("openid", openId);
			openIdMap.put("lang", lang);
			openIdmaplis.add(openIdMap);
		}
		map.put("user_list", openIdmaplis);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, JSONObject.toJSONString(map));
		if (null != jsonObject) {
			try {
				wuiLis = JSONArray.parseArray(jsonObject.getJSONArray("user_info_list").toJSONString(), WeixinUserInfo.class);
				response.success(wuiLis);
			} catch (Exception e) {
				int errCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errCode, errMsg);
				log.error("获取用户信息(批量)失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
	}

	/**
	 * 获取关注者列表
	 * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求
	 * @param accessToken 调用接口凭证
	 * @param nextOpenId 第一个拉取的openId，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings( { "unchecked" })
	public static Response<WeixinUserList> getUserList(String accessToken, String nextOpenId) {
		Response response = ResponseFactory.getInstance();
		WeixinUserList weixinUserList;

		if (null == nextOpenId)
			nextOpenId = "";

		// 拼接请求地址
		String requestUrl = USER_GET_URL.replace(Constants.ACCESS_TOKEN, accessToken).replace(Constants.NEXT_OPENID, nextOpenId);
		// 获取关注者列表
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				weixinUserList.setTotal(jsonObject.getLong("total"));
				weixinUserList.setCount(jsonObject.getLong("count"));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.parseArray(dataObject.getJSONArray("openid").toJSONString(), String.class));
				response.success(weixinUserList);
			} catch (JSONException e) {
				int errCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errCode, errMsg);
				log.error("获取关注者列表失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
	}

	/**
	 * 获取公众号的黑名单列表
	 * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求
	 * @param accessToken 调用接口凭证
	 * @param begin_openid 第一个拉取的openId，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings( { "unchecked" })
	public static Response<WeixinUserList> getBlackList(String accessToken, String begin_openid) {
		Response response = ResponseFactory.getInstance();
		WeixinUserList weixinUserList;

		begin_openid = StringUtils.isBlank(begin_openid) ? "" : begin_openid;
		// 拼接请求地址
		String requestUrl = TAGS_MEMBERS_GETBLACKLIST_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonData = "{\"begin_openid\": \"%s\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, begin_openid));
		// 如果请求成功
		if (null != jsonObject) {
			try {
				weixinUserList = new WeixinUserList();
				weixinUserList.setTotal(jsonObject.getLong("total"));
				weixinUserList.setCount(jsonObject.getLong("count"));
				weixinUserList.setNextOpenId(jsonObject.getString("next_openid"));
				JSONObject dataObject = (JSONObject) jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONArray.parseArray(dataObject.getJSONArray("openid").toJSONString(), String.class));
				response.success(weixinUserList);
			} catch (JSONException e) {
				int errCode = jsonObject.getIntValue(Constants.ERRCODE);
				String errMsg = jsonObject.getString(Constants.ERRMSG);
				response.fail(errCode, errMsg);
				log.error("获取公众号的黑名单列表失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		return response;
	}
	
	/**
	 * 拉黑用户
	 * 公众号可通过该接口来拉黑一批用户
	 * @param accessToken 接口访问凭证
	 * @return Response
	 */
	public static Response batchBlackList(String accessToken, List<String> openid_list) {
		Response response = ResponseFactory.getInstance();
		// 拼接请求地址
		String requestUrl = TAGS_MEMBERS_BATCHBLACKLIST_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		UserTagMember userTagMember = new UserTagMember(0, openid_list);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, JSONObject.toJSONString(userTagMember));
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if (0 == errCode) {
				response.success();
				log.info("批量拉黑用户成功 errcode:{} errmsg:{}", errCode, errMsg);
			} else {
				response.fail(errCode, errMsg);
				log.error("批量拉黑用户失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * 取消拉黑用户
	 * 公众号可通过该接口来拉黑一批用户
	 * @param accessToken 接口访问凭证
	 * @return Response
	 */
	public static Response batchUnblackList(String accessToken, List<String> openid_list) {
		Response response = ResponseFactory.getInstance();
		// 拼接请求地址
		String requestUrl = TAGS_MEMBERS_BATCHUNBLACKLIST_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 需要提交的json数据
		UserTagMember userTagMember = new UserTagMember(0, openid_list);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, JSONObject.toJSONString(userTagMember));
		if (null != jsonObject) {
			int errCode = jsonObject.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObject.getString(Constants.ERRMSG);
			if (0 == errCode) {
				response.success();
				log.info("批量取消拉黑用户成功 errcode:{} errmsg:{}", errCode, errMsg);
			} else {
				response.fail(errCode, errMsg);
				log.error("批量取消拉黑用户失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
}
