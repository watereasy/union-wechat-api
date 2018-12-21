package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.shake.Device;
import com.jd.union.wechat.api.shake.Group;
import com.jd.union.wechat.api.shake.Groups;
import com.jd.union.wechat.api.shake.Page;
import com.jd.union.wechat.api.shake.Statistics;
import com.jd.union.wechat.api.shake.resp.AccountAuditStatusResp;
import com.jd.union.wechat.api.shake.resp.DeviceApplyIdResp;
import com.jd.union.wechat.api.shake.resp.DeviceApplyStatusResp;
import com.jd.union.wechat.api.shake.resp.DeviceSearchResp;
import com.jd.union.wechat.api.shake.resp.GroupDetailResp;
import com.jd.union.wechat.api.shake.resp.PageSearchResp;
import com.jd.union.wechat.api.shake.resp.RelationSearchResp;
import com.jd.union.wechat.api.shake.resp.UserGetShakeInfoResp;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 微信摇一摇
 * @author zhangjianhui
 *
 */
@Slf4j
public class ShakeUtil {
	
	/**
	 * 申请开通功能
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg 入参
	 * @return String
	 */
	public static String accountRegister(String accessToken, String jsonMsg) {
		String data = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/account/register?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				data = jsonObject.getString("data");
				log.info("摇一摇申请开通功能成功 data:{}", data);
			} else {
				log.error("摇一摇申请开通功能失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return data;
	}
	
	/**
	 * 查询审核状态
	 * @param accessToken 接口访问凭证
	 * @return AccountAuditStatusResp
	 */
	public AccountAuditStatusResp accountAuditStatus(String accessToken) {
		AccountAuditStatusResp auditStatus = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/account/auditstatus?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				JSONObject data = jsonObject.getJSONObject("data");
				auditStatus = JSONObject.parseObject(data.toJSONString(), AccountAuditStatusResp.class);
				log.info("摇一摇查询审核状态成功 data:{}", data);
			} else {
				log.error("摇一摇查询审核状态失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return auditStatus;
	}
	
	/**
	 * 申请设备ID
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return DeviceApplyIdResp
	 */
	public static DeviceApplyIdResp deviceApplyId(String accessToken, String jsonMsg) {
		DeviceApplyIdResp appIdResp = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/applyid?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				JSONObject data = jsonObject.getJSONObject("data");
				appIdResp = JSONObject.parseObject(data.toJSONString(), DeviceApplyIdResp.class);
				log.info("摇一摇申请设备ID成功 data:{}", data);
			} else {
				log.error("摇一摇申请设备ID失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return appIdResp;
	}
	
	/**
	 * 查询设备ID申请审核状态
	 * @param accessToken 接口访问凭证
	 * @param applyId applyId
	 * @return DeviceApplyStatusResp
	 */
	public static DeviceApplyStatusResp deviceApplyStatus(String accessToken, int applyId) {
		DeviceApplyStatusResp applyStatusResp = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/applystatus?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"apply_id\":\"%d\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, applyId));
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				JSONObject data = jsonObject.getJSONObject("data");
				applyStatusResp = JSONObject.parseObject(data.toJSONString(), DeviceApplyStatusResp.class);
				log.info("摇一摇查询设备ID申请审核状态成功 data:{}", data);
			} else {
				log.error("摇一摇查询设备ID申请审核状态失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return applyStatusResp;
	}
	
	/**
	 * 编辑设备信息
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return String
	 */
	public static String deviceUpdate(String accessToken, String jsonMsg) {
		String data = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				data = jsonObject.getString("data");
				log.info("摇一摇编辑设备信息成功 data:{}", data);
			} else {
				log.error("摇一摇编辑设备信息失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return data;
	}
	
	/**
	 * 配置设备与门店的关联关系
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return String
	 */
	public static String deviceBindLocation(String accessToken, String jsonMsg) {
		String data = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/bindlocation?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				data = jsonObject.getString("data");
				log.info("摇一摇配置设备与门店的关联关系成功 data:{}", data);
			} else {
				log.error("摇一摇配置设备与门店的关联关系失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return data;
	}
	
	/**
	 * 查询设备列表
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return DeviceSearchResp
	 */
	@SuppressWarnings("unchecked")
	public static DeviceSearchResp deviceSearch(String accessToken, String jsonMsg) {
		DeviceSearchResp searchResp = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/search?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				searchResp = new DeviceSearchResp();
				JSONObject dataObj = jsonObject.getJSONObject("data");
				searchResp.setDevices(JSONArray.parseArray(dataObj.getJSONArray("devices").toJSONString(), Device.class));
				searchResp.setTotal_count(dataObj.getInteger("total_count"));
				log.info("摇一摇查询设备列表成功 data:{}", dataObj);
			} else {
				log.error("摇一摇查询设备列表失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return searchResp;
	}
	
	/**
	 * 新增页面
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return String
	 */
	public static String pageAdd(String accessToken, String jsonMsg) {
		String data = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/page/add?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				data = jsonObject.getString("data");
				log.info("摇一摇新增页面成功 data:{}", data);
			} else {
				log.error("摇一摇新增页面失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return data;
	}

	/**
	 * 编辑页面信息
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return String
	 */
	public static String pageUpdate(String accessToken, String jsonMsg) {
		String data = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/page/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				data = jsonObject.getString("data");
				log.info("摇一摇编辑页面信息成功 data:{}", data);
			} else {
				log.error("摇一摇编辑页面信息失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return data;
	}

	/**
	 * 查询页面列表
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return PageSearchResp
	 */
	@SuppressWarnings("unchecked")
	public static PageSearchResp pageSearch(String accessToken, String jsonMsg) {
		PageSearchResp searchResp = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/page/search?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				searchResp = new PageSearchResp();
				JSONObject dataObj = jsonObject.getJSONObject("data");
				searchResp.setPages(JSONArray.parseArray(dataObj.getJSONArray("pages").toJSONString(), Page.class));
				searchResp.setTotal_count(dataObj.getInteger("total_count"));
				log.info("摇一摇查询设备列表成功 data:{}", dataObj);
			} else {
				log.error("摇一摇查询设备列表失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return searchResp;
	}
	
	/**
	 * 删除页面
	 * @param accessToken 接口访问凭证
	 * @param pageId pageId
	 * @return String
	 */
	public static String pageDelete(String accessToken, int pageId) {
		String data = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/page/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"page_id\":\"%d\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, pageId));
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				data = jsonObject.getString("data");
				log.info("摇一摇删除页面成功 data:{}", data);
			} else {
				log.error("摇一摇删除页面失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return data;
	}
	
	/**
	 * 上传图片素材
	 * @param accessToken 接口访问凭证
	 * @param type(Icon:摇一摇页面展示的icon图;License:申请开通摇一摇周边功能时需上传的资质文件;若不传type,则默认type=icon)
	 * @param mediaFileUrl url
	 * @return String
	 */
	public static String materialAdd(String accessToken, String type, String mediaFileUrl) {
		String data = null;
		// 拼装请求地址
		String requestUrl = "https://api.weixin.qq.com/shakearound/material/add?access_token=ACCESS_TOKEN&type=TYPE";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		// 使用JSON解析返回结果
		JSONObject jsonObject = JSONObject.parseObject(CommonUtil.uploadWX(requestUrl, mediaFileUrl, null));
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				data = jsonObject.getString("data");
				log.info("摇一摇上传图片素材 data:{}", data);
			} else {
				log.error("摇一摇上传图片素材 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return data;
	}
	
	/**
	 * 配置设备与页面的关联关系
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return String
	 */
	public static String deviceBindPage(String accessToken, String jsonMsg) {
		String data = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/bindpage?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				data = jsonObject.getString("data");
				log.info("摇一摇配置设备与页面的关联关系成功 data:{}", data);
			} else {
				log.error("摇一摇配置设备与页面的关联关系失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return data;
	}
	
	/**
	 * 查询设备与页面的关联关系
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return RelationSearchResp
	 */
	@SuppressWarnings("unchecked")
	public static RelationSearchResp relationSearch(String accessToken, String jsonMsg) {
		RelationSearchResp searchResp = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/relation/search?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				searchResp = new RelationSearchResp();
				JSONObject dataObj = jsonObject.getJSONObject("data");
				searchResp.setDevices(JSONArray.parseArray(dataObj.getJSONArray("relations").toJSONString(), Device.class));
				searchResp.setTotal_count(dataObj.getInteger("total_count"));
				log.info("摇一摇查询设备与页面的关联关系成功 data:{}", dataObj);
			} else {
				log.error("摇一摇查询设备与页面的关联关系失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return searchResp;
	}
	
	/**
	 * 获取摇周边的设备及用户信息
	 * @param accessToken 接口访问凭证
	 * @param ticket ticket
	 * @param needPoi needPoi
	 * @return UserGetShakeInfoResp
	 */
	public static UserGetShakeInfoResp userGetShakeInfo(String accessToken, String ticket, int needPoi) {
		UserGetShakeInfoResp shakeInfo = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/user/getshakeinfo?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"ticket\":\"%s\", \"need_poi\":\"%d\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, ticket, needPoi));
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				JSONObject data = jsonObject.getJSONObject("data");
				shakeInfo = JSONObject.parseObject(data.toJSONString(), UserGetShakeInfoResp.class);
				log.info("摇一摇获取摇周边的设备及用户信息成功 data:{}", data);
			} else {
				log.error("摇一摇获取摇周边的设备及用户信息失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return shakeInfo;
	}
	
	/**
	 * 以设备为维度的数据统计接口
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public static List<Statistics> statisticsDevice(String accessToken, String jsonMsg) {
		List<Statistics> statisticsLis = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/statistics/device?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				JSONArray dataArr = jsonObject.getJSONArray("data");
				statisticsLis = JSONArray.parseArray(dataArr.toJSONString(), Statistics.class);
				log.info("摇一摇以设备为维度的数据统计接口成功 data:{}", dataArr);
			} else {
				log.error("摇一摇以设备为维度的数据统计接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return statisticsLis;
	}
	
	/**
	 * 批量查询设备统计数据接口
	 * @param accessToken 接口访问凭证
	 * @param date date
	 * @param pageIndex pageIndex
	 * @return JSONObject
	 */
	public static JSONObject statisticsDeviceList(String accessToken, long date, int pageIndex) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/statistics/devicelist?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"date\":\"%d\", \"page_index\":\"%d\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, date, pageIndex));
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject;
				log.info("摇一摇批量查询设备统计数据接口成功 data:{}", json);
			} else {
				log.error("摇一摇批量查询设备统计数据接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
	/**
	 * 以页面为维度的数据统计接口
	 * @param accessToken 接口访问凭证
	 * @param page_id page_id
	 * @param begin_date begin_date
	 * @param end_date end_date
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public static List<Statistics> statisticsPage(String accessToken, int page_id, long begin_date, long end_date) {
		List<Statistics> statisticsLis = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/statistics/page?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"page_id\":\"%d\", \"begin_date\":\"%d\", \"end_date\":\"%d\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, page_id, begin_date, end_date));
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				JSONArray dataArr = jsonObject.getJSONArray("data");
				statisticsLis = JSONArray.parseArray(dataArr.toJSONString(), Statistics.class);
				log.info("摇一摇以设备为维度的数据统计接口成功 data:{}", dataArr);
			} else {
				log.error("摇一摇以设备为维度的数据统计接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return statisticsLis;
	}
	
	/**
	 * 批量查询页面统计数据接口
	 * @param accessToken 接口访问凭证
	 * @param date date
	 * @param pageIndex pageIndex
	 * @return JSONObject
	 */
	public static JSONObject statisticsPageList(String accessToken, long date, int pageIndex) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/statistics/pagelist?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		String jsonMsg = "{\"date\":\"%d\", \"page_index\":\"%d\"}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, date, pageIndex));
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject;
				log.info("摇一摇批量查询设备统计数据接口成功 data:{}", json);
			} else {
				log.error("摇一摇批量查询设备统计数据接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
	// TODO 红包接口
	
	/**
	 * 创建红包
	 * @param accessToken 接口访问凭证
	 * @param useTemplate useTemplate
	 * @param logoUrl logoUrl
	 * @param jsonMsg jsonMsg
	 * @return JSONObject
	 */
	public static JSONObject lotteryAddlotteryinfo(String accessToken, String useTemplate, String logoUrl, String jsonMsg) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/lottery/addlotteryinfo?access_token=ACCESS_TOKEN&use_template=USE_TEMPLATE&logo_url=LOGO_URL";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("USE_TEMPLATE", useTemplate).replace("LOGO_URL", logoUrl);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject;
				log.info("摇一摇创建红包活动接口成功 data:{}", json);
			} else {
				log.error("摇一摇创建红包活动接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
	/**
	 * 录入红包
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return JSONObject
	 */
	public static JSONObject lotterySetprizebucket(String accessToken, String jsonMsg) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/lottery/setprizebucket?access_token=ACCESSTOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject;
				log.info("摇一摇录入红包活动接口成功 data:{}", json);
			} else {
				log.error("摇一摇录入红包活动接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
	/**
	 * 设置红包活动抽奖开关
	 * @param accessToken 接口访问凭证
	 * @param lotteryId lotteryId
	 * @param onoff 0：关闭，1：开启
	 * @return JSONObject
	 */
	public static JSONObject lotterySetprizebucket(String accessToken, String lotteryId, String onoff) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/lottery/setlotteryswitch?access_token=ACCESS_TOKEN&lottery_id=LOTTERY_ID&onoff=ONOFF";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("LOTTERY_ID", lotteryId).replace("ONOFF", onoff);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject;
				log.info("设置红包活动抽奖开关动接口成功 data:{}", json);
			} else {
				log.error("设置红包活动抽奖开关接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
	/**
	 * 新增分组
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return Group
	 */
	public static Group groupAdd(String accessToken, String jsonMsg) {
		Group group = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/group/add?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				group = JSONObject.parseObject(jsonObject.getJSONObject("data").toJSONString(), Group.class);
				log.info("新增分组接口成功 data:{}", jsonObject);
			} else {
				log.error("新增分组接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return group;
	}
	
	/**
	 * 编辑分组
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return JSONObject
	 */
	public static JSONObject groupUpdate(String accessToken, String jsonMsg) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/group/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject.getJSONObject("data");
				log.info("编辑分组接口成功 data:{}", jsonObject);
			} else {
				log.error("编辑分组接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
	/**
	 * 删除分组
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return JSONObject
	 */
	public static JSONObject groupDel(String accessToken, String jsonMsg) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/group/delete?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject.getJSONObject("data");
				log.info("删除分组接口成功 data:{}", jsonObject);
			} else {
				log.error("删除分组接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
	/**
	 * 查询分组列表
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return Groups
	 */
	@SuppressWarnings("unchecked")
	public static Groups groupGetlist(String accessToken, String jsonMsg) {
		Groups groups = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/group/getlist?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				JSONObject dataObj = jsonObject.getJSONObject("data");
				groups = new Groups();
				groups.setTotal_count(dataObj.getInteger("total_count"));
				groups.setGroups(JSONArray.parseArray(dataObj.getJSONArray("groups").toJSONString(), Group.class));
				log.info("查询分组列表接口成功 data:{}", jsonObject);
			} else {
				log.error("查询分组列表接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return groups;
	}
	
	/**
	 * 查询分组详情
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return GroupDetailResp
	 */
	@SuppressWarnings("unchecked")
	public static GroupDetailResp groupGetdetail(String accessToken, String jsonMsg) {
		GroupDetailResp groupDetailResp = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/group/getdetail?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				JSONObject dataObj = jsonObject.getJSONObject("data");
				groupDetailResp = new GroupDetailResp();
				groupDetailResp.setGroup_id(dataObj.getInteger("total_count"));
				groupDetailResp.setGroup_name(dataObj.getString("group_name"));
				groupDetailResp.setTotal_count(dataObj.getInteger("total_count"));
				groupDetailResp.setDevices(JSONArray.parseArray(dataObj.getJSONArray("devices").toJSONString(), Device.class));
				log.info("查询分组详情接口成功 data:{}", jsonObject);
			} else {
				log.error("查询分组详情接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return groupDetailResp;
	}
	
	/**
	 * 添加设备到分组
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return JSONObject
	 */
	public static JSONObject groupAdddevice(String accessToken, String jsonMsg) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/group/adddevice?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject.getJSONObject("data");
				log.info("添加设备到分组接口成功 data:{}", jsonObject);
			} else {
				log.error("添加设备到分组接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
	/**
	 * 从分组中移除设备
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg jsonMsg
	 * @return JSONObject
	 */
	public static JSONObject groupDeldevice(String accessToken, String jsonMsg) {
		JSONObject json = null;
		String requestUrl = "https://api.weixin.qq.com/shakearound/device/group/deletedevice?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(jsonObject != null){
			int errCode = jsonObject.getInteger("errcode");
			String errMsg = jsonObject.getString("errmsg");
			if(errCode == 0){
				json = jsonObject.getJSONObject("data");
				log.info("从分组中移除设备接口成功 data:{}", jsonObject);
			} else {
				log.error("从分组中移除设备接口失败 errcode:{} errmsg:{}", errCode,
						errMsg);
			}
		}
		
		return json;
	}
	
}
