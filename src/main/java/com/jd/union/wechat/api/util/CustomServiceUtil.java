package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.factory.ResponseFactory;
import com.jd.union.wechat.api.message.resp.Article;
import com.jd.union.wechat.api.message.resp.Music;
import com.jd.union.wechat.api.model.CustomAccount;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 客服管理
 * Created by zhangjianhui6 on 2018/12/11.
 *
 **/
@Slf4j
public class CustomServiceUtil {

    /** 添加客服账号(POST) */
    private static final String CUSTOMSERVICE_GETKFLIST_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
    /** 添加客服账号(POST) */
    private static final String CUSTOMSERVICE_KFACCOUNT_ADD_URL = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
    /** 邀请绑定客服帐号(POST) */
    private static final String CUSTOMSERVICE_KFACCOUNT_INVITEWORKER_URL = "https://api.weixin.qq.com/customservice/kfaccount/inviteworker?access_token=ACCESS_TOKEN";
    /** 修改客服账号(POST) */
    private static final String CUSTOMSERVICE_KFACCOUNT_UPDATE_URL = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
    /** 删除客服账号(GET) */
    private static final String CUSTOMSERVICE_KFACCOUNT_DEL_URL = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN";
    /** 上传客服头像(POST/FORM) */
    private static final String CUSTOMSERVICE_KFACCOUNT_UPLOADHEADIMG_URL = "https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";

    /**
     * 获取客服基本信息
     * @param accessToken 调用凭据
     * @return 客户信息集合
     */
    @SuppressWarnings("unchecked")
    public static Response<List<CustomAccount>> getAllCustomAccount(String accessToken) {
        Response response = ResponseFactory.getInstance();
        List<CustomAccount> list;
        String requestUrl = CUSTOMSERVICE_GETKFLIST_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, Constants.EMPTY);
        if(jsonObject != null) {
            try {
                list = JSONArray.parseArray(jsonObject.getJSONArray("kf_list").toJSONString(), CustomAccount.class);
                response.success(list);
            } catch (JSONException e) {
                int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
                String errorMsg = jsonObject.getString(Constants.ERRMSG);
                response.fail(errorCode, errorMsg);
                log.error("获取客服基本信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return response;
    }

    /**
     * 添加客服账号 (最多100个,可申请扩充)
     * @param accessToken 调用凭据
     * @param kfAccount 完整客服帐号，格式为：帐号前缀@公众号微信号，帐号前缀最多10个字符，必须是英文、数字字符或者下划线，后缀为公众号微信号，长度不超过30个字符
     * @param nickname 客服昵称，最长16个字
     * @return Response
     */
    public static Response createCustomAccount(String accessToken, String kfAccount, String nickname) {
        Response response = ResponseFactory.getInstance();
        String requestUrl = CUSTOMSERVICE_KFACCOUNT_ADD_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        String jsonMsg = "{\"kf_account\":\"%s\",\"nickname\":\"%s\"}";
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonMsg, kfAccount, nickname));
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            if (0 == errCode) {
                response.success();
                log.info("添加客服帐号成功 errcode:{} errmsg:{}", errCode, errMsg);
            } else {
                response.fail(errCode, errMsg);
                log.error("添加客服帐号失败 errcode:{} errmsg:{}", errCode, errMsg);
            }
        }

        return response;
    }


    /**
     * 邀请绑定客服帐号
     * @param accessToken 调用凭据
     * @param kfAccount 完整客服帐号，格式为：帐号前缀@公众号微信号
     * @param inviteWx 接收绑定邀请的客服微信号
     * @return Response
     */
    public static Response bindingCustomAccount(String accessToken, String kfAccount, String inviteWx) {
        Response response = ResponseFactory.getInstance();
        String requestUrl = CUSTOMSERVICE_KFACCOUNT_INVITEWORKER_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        String jsonMsg = "{\"kf_account\":\"%s\",\"invite_wx\":\"%s\"}";
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, String.format(jsonMsg, kfAccount, inviteWx));
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            if (0 == errCode) {
                response.success();
                log.info("邀请绑定客服帐号 errcode:{} errmsg:{}", errCode, errMsg);
            } else {
                response.fail(errCode, errMsg);
                log.error("邀请绑定客服帐号 errcode:{} errmsg:{}", errCode, errMsg);
            }
        }

        return response;
    }

    /**
     * 修改客服帐号
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return true/false
     */
    public static Response updateCustomAccount(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        String requestUrl = CUSTOMSERVICE_KFACCOUNT_UPDATE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        // 发送客服消息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            if (0 == errCode) {
                response.success();
                log.info("修改客服帐号 errcode:{} errmsg:{}", errCode, errMsg);
            } else {
                response.fail(errCode, errMsg);
                log.error("修改客服帐号 errcode:{} errmsg:{}", errCode, errMsg);
            }
        }

        return response;
    }

    /**
     * 删除客服帐号
     * @param accessToken 调用凭据
     * @param jsonMsg 消息
     * @return Response
     */
    public static Response deleteCustomAccount(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        String requestUrl = CUSTOMSERVICE_KFACCOUNT_DEL_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_GET, jsonMsg);
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            if (0 == errCode) {
                response.success();
                log.info("删除客服帐号 errcode:{} errmsg:{}", errCode, errMsg);
            } else {
                response.fail(errCode, errMsg);
                log.error("删除客服帐号 errcode:{} errmsg:{}", errCode, errMsg);
            }
        }

        return response;
    }

    /**
     * 设置客服帐号的头像
     * @param accessToken 调用凭据
     * @param kfAccount 客服账号
     * @param mediaFileUrl 图片URL
     * @return Response
     */
    public static Response setCustomAccountAvatar(String accessToken, String kfAccount, String mediaFileUrl) {
        Response response = ResponseFactory.getInstance();
        String requestUrl = CUSTOMSERVICE_KFACCOUNT_UPLOADHEADIMG_URL.replace(Constants.ACCESS_TOKEN, accessToken).replace(Constants.KFACCOUNT, kfAccount);
        String jsonMsg = CommonUtil.uploadWX(requestUrl, mediaFileUrl, Constants.EMPTY);

        JSONObject jsonObject = JSONObject.parseObject(jsonMsg);
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            if (0 == errCode) {
                response.success();
                log.info("设置客服帐号的头像成功 errcode:{} errmsg:{}", errCode, errMsg);
            } else {
                response.fail(errCode, errMsg);
                log.error("设置客服帐号的头像失败 errcode:{} errmsg:{}", errCode, errMsg);
            }
        }

        return response;
    }

    /**
     * 组装文本客服消息
     * TODO 以某个客服帐号来发消息后续实现...
     * @param openId 消息发送对象
     * @param content 文本消息内容
     * @return 消息体
     */
    public static String makeTextCustomMessage(String openId, String content) {
        // 对消息内容的双引号转义
        content = content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
        return String.format(jsonMsg, openId, content);
    }

    /**
     * 组装图片客服消息
     *
     * @param openId 消息发送对象
     * @param mediaId 媒体文件id
     * @return 消息体
     */
    public static String makeImageCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
     * 组装语音客服消息
     *
     * @param openId 消息发送对象
     * @param mediaId 媒体文件id
     * @return 消息体
     */
    public static String makeVoiceCustomMessage(String openId, String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId);
    }

    /**
     * 组装视频客服消息
     *
     * @param openId 消息发送对象
     * @param mediaId 媒体文件id
     * @param thumbMediaId 视频消息缩略图的媒体id
     * @return 消息体
     */
    public static String makeVideoCustomMessage(String openId, String mediaId, String thumbMediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId, mediaId, thumbMediaId);
    }

    /**
     * 组装音乐客服消息
     *
     * @param openId 消息发送对象
     * @param music 音乐对象
     * @return 消息体
     */
    public static String makeMusicCustomMessage(String openId, Music music) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
        jsonMsg = String.format(jsonMsg, openId, JSONObject.toJSONString(music));
        // 将jsonMsg中的thumbmediaid替换为thumb_media_id
        jsonMsg = jsonMsg.replace("thumbmediaid", "thumb_media_id");
        return jsonMsg;
    }

    /**
     * 组装图文客服消息
     *
     * @param openId 消息发送对象
     * @param articleList 图文消息列表
     * @return String
     */
    public static String makeNewsCustomMessage(String openId, List<Article> articleList) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
        jsonMsg = String.format(jsonMsg, openId, JSONArray.toJSONString(articleList).replaceAll("\"", "\\\""));
        // 将jsonMsg中的picUrl替换为picurl
        jsonMsg = jsonMsg.replace("picUrl", "picurl");
        return jsonMsg;
    }

}
