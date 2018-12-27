package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.factory.ResponseFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义菜单工具类
 * Created by zhangjianhui6 on 2018/12/7.
 * 
 **/
@Slf4j
public class MenuUtil {
	
	// 菜单创建(POST)
	private static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 菜单查询(GET)
	private static final String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 菜单删除(GET)
	private static final String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	// 个性化菜单接口，条件：用户标签、性别、手机操作系统、地区、语言
	// 个性化菜单创建(POST)
	private static final String MENU_CONDITIONAL_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";
	// 个性化菜单删除(POST)
	private static final String MENU_CONDITIONAL_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN";
	// 测试个性化菜单匹配结果(POST)
	private static final String MENU_CONDITIONAL_TRYMATCH_URL = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=ACCESS_TOKEN";
	// 获取自定义菜单配置接口(GET)
	private static final String MENU_INFO_GET_URL = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";
	/**
	 * 创建菜单
	 * 
	 * @param accessToken 凭证
	 * @param jsonStr 菜单字符串
	 * @return Response
	 */
	public static Response createMenu(String accessToken, String jsonStr) {
		Response response = ResponseFactory.getInstance();
		String requestUrl = MENU_CREATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 发起POST请求创建菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonStr);
		
		if(jsonObj != null){
			int errCode = jsonObj.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObj.getString(Constants.ERRMSG);
			if(errCode == 0){
				response.success();
				log.info("创建菜单成功");
			} else {
				response.fail(errCode, errMsg);
				log.error("创建菜单失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * TODO 解析出参
	 * 查询菜单
	 * 
	 * @param accessToken 凭证
	 * @return 获取默认菜单和全部个性化菜单信息
	 */
	public static JSONObject getMenu(String accessToken) {
		String requestUrl = MENU_GET_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 发起GET请求查询菜单
		return CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
	}
	
	/**
	 * 删除菜单
	 * 
	 * @param accessToken 凭证
	 * @return true|成功，false|失败
	 */
	public static Response deleteMenu(String accessToken) {
		Response response = ResponseFactory.getInstance();
		String requestUrl = MENU_DELETE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 发起GET请求删除菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
		
		if(jsonObj != null){
			int errCode = jsonObj.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObj.getString(Constants.ERRMSG);
			if(errCode == 0){
				response.success();
				log.info("删除菜单成功");
			} else {
				response.fail(errCode, errMsg);
				log.error("删除菜单失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * 创建个性化菜单
	 * @param accessToken 凭证
	 * @param jsonStr 字符串
	 * @return Response
	 */
	@SuppressWarnings({"unchecked"})
	public static Response<String> createConditionalMenu(String accessToken, String jsonStr) {
		Response response = ResponseFactory.getInstance();
		String result;
		String requestUrl = MENU_CONDITIONAL_CREATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 发起POST请求创建菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonStr);
		
		if(jsonObj != null){
			if(jsonObj.containsKey("menuid")) {
				result = jsonObj.getString("menuid");
				response.success(result);
				log.info("创建个性化菜单成功 menuid:{}", result);
			}else{
				int errCode = jsonObj.getIntValue(Constants.ERRCODE);
				String errMsg = jsonObj.getString(Constants.ERRMSG);
				response.fail(errCode, errMsg);
				log.error("创建个性化菜单失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * 删除个性化菜单
	 * 
	 * @param accessToken 凭证
	 * @param menuid 菜单id
	 * @return Response
	 */
	public static Response deleteConditionalMenu(String accessToken, String menuid) {
		Response response = ResponseFactory.getInstance();
		String requestUrl = MENU_CONDITIONAL_DELETE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonData = "{\"menuid\" : \"%s\" }";
		// 发起请求删除个性化菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, menuid));
		
		if(jsonObj != null){
			int errCode = jsonObj.getIntValue(Constants.ERRCODE);
			String errMsg = jsonObj.getString(Constants.ERRMSG);
			if(errCode == 0){
				response.success();
				log.info("删除个性化菜单成功");
			} else {
				response.fail(errCode, errMsg);
				log.error("删除个性化菜单失败 errcode:{} errmsg:{}", errCode, errMsg);
			}
		}
		
		return response;
	}
	
	/**
	 * TODO 解析出参
	 * 测试个性化菜单匹配结果
	 * 
	 * @param accessToken 凭证
	 * @param userid:OpenID|微信号
	 * @return
	 */
	public static JSONObject tryMatch(String accessToken, String userid) {
		String requestUrl = MENU_CONDITIONAL_TRYMATCH_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		String jsonData = "{\"user_id\" : \"%s\" }";
		// 发起请求删除个性化菜单
		JSONObject jsonObj = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonData, userid));
		
		return jsonObj;
	}
	
	/**
	 * 解析出参
	 * 获取自定义菜单配置接口
	 * 
	 * @param accessToken 凭证
	 * @return JSONObject
	 */
	public static JSONObject getMenuInfo(String accessToken) {
		String requestUrl = MENU_INFO_GET_URL.replace(Constants.ACCESS_TOKEN, accessToken);
		// 发起GET请求查询菜单
		return CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
	}

}
