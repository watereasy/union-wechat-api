package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.message.resp.Article;
import com.jd.union.wechat.api.message.resp.Music;
import com.jd.union.wechat.api.model.CustomAccount;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * 客服管理
 * Created by zhangjianhui6 on 2018/12/11.
 *
 **/
@Slf4j
public class CustomServiceUtil {

    /**
     * 添加客服账号 (最多100个,可申请扩充)
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return true/false
     */
    public static boolean createCustomAccount(String accessToken, String jsonMsg) {
        log.info("消息内容：{}", jsonMsg);
        boolean result = false;
        String requestUrl = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
        // 拼接请求地址
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        // 发送客服消息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
        if (null != jsonObject) {
            int errorCode = jsonObject.getIntValue("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
                log.info("添加客服帐号成功 errcode:{} errmsg:{}", errorCode,
                        errorMsg);
            } else {
                log.error("添加客服帐号失败 errcode:{} errmsg:{}", errorCode,
                        errorMsg);
            }
        }

        return result;
    }

    /**
     * 修改客服帐号
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return true/false
     */
    public static boolean updateCustomAccount(String accessToken, String jsonMsg) {
        log.info("消息内容：{}", jsonMsg);
        boolean result = false;
        String requestUrl = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
        // 拼接请求地址
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        // 发送客服消息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
        if (null != jsonObject) {
            int errorCode = jsonObject.getIntValue("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
                log.info("修改客服帐号 errcode:{} errmsg:{}", errorCode,
                        errorMsg);
            } else {
                log.error("修改客服帐号 errcode:{} errmsg:{}", errorCode,
                        errorMsg);
            }
        }

        return result;
    }

    /**
     * 删除客服帐号
     * @param accessToken 调用凭据
     * @param jsonMsg 消息
     * @return true/false
     */
    public static boolean deleteCustomAccount(String accessToken, String jsonMsg) {
        log.info("消息内容：{}", jsonMsg);
        boolean result = false;
        String requestUrl = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN";
        // 拼接请求地址
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        // 发送客服消息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
        if (null != jsonObject) {
            int errorCode = jsonObject.getIntValue("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
                log.info("删除客服帐号 errcode:{} errmsg:{}", errorCode, errorMsg);
            } else {
                log.error("删除客服帐号 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }

        return result;
    }

    /**
     * 设置客服帐号的头像
     * @param accessToken 调用凭据
     * @param kfAccount 客服账号
     * @param mediaFileUrl 图片URL
     * @return
     */
    public static boolean setCustomAccountAvatar(String accessToken, String kfAccount, String mediaFileUrl) {
        boolean result = false;
        String requestUrl = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("KFACCOUNT", kfAccount);
        String jsonMsg = CommonUtil.uploadWX(requestUrl, mediaFileUrl, null);

        JSONObject jsonObject = JSONObject.parseObject(jsonMsg);
        if (null != jsonObject) {
            int errorCode = jsonObject.getIntValue("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
                log.info("客服消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
            } else {
                log.error("客服消息发送失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }

        return result;
    }

    /**
     * 获取客服基本信息
     * @param accessToken 调用凭据
     * @return 客户信息集合
     */
    @SuppressWarnings("unchecked")
    public static List<CustomAccount> getAllCustomAccount(String accessToken) {
        List<CustomAccount> list = new ArrayList<CustomAccount>();
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
        if(jsonObject != null) {
            try {
                list = JSONArray.parseArray(jsonObject.getJSONArray("kf_list").toJSONString(), CustomAccount.class);
            } catch (JSONException e) {
                log.error("获取客服基本信息失败 errcode:{} errmsg:{}",
                        jsonObject.getIntValue("errcode"),
                        jsonObject.getString("errmsg"));
            }
        }
        return list;
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
     * @return
     */
    public static String makeNewsCustomMessage(String openId, List<Article> articleList) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
        jsonMsg = String.format(jsonMsg, openId, JSONArray.toJSONString(articleList).replaceAll("\"", "\\\""));
        // 将jsonMsg中的picUrl替换为picurl
        jsonMsg = jsonMsg.replace("picUrl", "picurl");
        return jsonMsg;
    }

    /**
     * 发送客服消息
     *
     * @param accessToken 接口访问凭证
     * @param jsonMsg json格式的客服消息（包括touser、msgtype和消息内容）
     * @return true | false
     */
    public static boolean sendCustomMessage(String accessToken, String jsonMsg) {
        log.info("消息内容：{}", jsonMsg);
        boolean result = false;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        // 拼接请求地址
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
        // 发送客服消息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
        if (null != jsonObject) {
            int errorCode = jsonObject.getIntValue("errcode");
            String errorMsg = jsonObject.getString("errmsg");
            if (0 == errorCode) {
                result = true;
                log.info("客服消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
            } else {
                log.error("客服消息发送失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }

        return result;
    }
}
