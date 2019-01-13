package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.factory.ResponseFactory;
import com.jd.union.wechat.api.model.MassResult;
import com.jd.union.wechat.api.model.WeixinMedia;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageUtil {

    /** 添加客服账号(POST) */
    private static final String MESSAGE_CUSTOM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    /** 上传图文消息素材【订阅号与服务号认证后均可用】(POST) */
    private static final String MEDIA_UPLOADNEWS_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
    /** 根据标签进行群发【订阅号与服务号认证后均可用】(POST) */
    private static final String MESSAGE_MASS_SENDALL_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
    /** 根据OpenID列表群发【订阅号不可用，服务号认证后可用】(POST) */
    private static final String MESSAGE_MASS_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
    /** 删除群发【订阅号与服务号认证后均可用】(POST) */
    private static final String MESSAGE_MASS_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
    /** 预览接口【订阅号与服务号认证后均可用】(POST) */
    private static final String MESSAGE_MASS_PREVIEW_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
    /** 查询群发消息发送状态【订阅号与服务号认证后均可用】(POST) */
    private static final String MESSAGE_MASS_GET_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";
    /**
     * 发送客服消息
     *
     * @param accessToken 接口访问凭证
     * @param jsonMsg json格式的客服消息（包括touser、msgtype和消息内容）
     * @return Response
     */
    public static Response sendCustomMessage(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        String requestUrl = MESSAGE_CUSTOM_SEND_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        // 拼接请求地址
        // 发送客服消息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            if (0 == errCode) {
                response.success();
                log.info("客服消息发送成功 errcode:{} errmsg:{}", errCode, errMsg);
            } else {
                response.fail(errCode, errMsg);
                log.error("客服消息发送失败 errcode:{} errmsg:{}", errCode, errMsg);
            }
        }

        return response;
    }

    /**
     * 上传图文消息素材【订阅号与服务号认证后均可用】
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return 上传后素材明细
     */
    @SuppressWarnings({"unchecked"})
    public static Response<WeixinMedia> uploadNews(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        WeixinMedia weixinMedia;
        String requestUrl = MEDIA_UPLOADNEWS_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
        if (null != jsonObject) {
            if (jsonObject.containsKey("media_id ")) {
                weixinMedia = new WeixinMedia();
                weixinMedia.setType(jsonObject.getString("type"));
                weixinMedia.setMediaId(jsonObject.getString("media_id"));
                weixinMedia.setCreatedAt(jsonObject.getLong("created_at"));
                response.success(weixinMedia);
                log.info("上传图文消息素材成功 errcode:{} errmsg:{}");
            } else {
                int errorCode = jsonObject.getIntValue(Constants.ERRCODE);
                String errorMsg = jsonObject.getString(Constants.ERRMSG);
                response.fail(errorCode, errorMsg);
                log.error("上传图文消息素材失败 errcode:{} errmsg:{}", errorCode,
                        errorMsg);
            }
        }

        return response;
    }

    /**
     * 根据标签进行群发【订阅号与服务号认证后均可用】
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return 群发结果
     */
    @SuppressWarnings({"unchecked"})
    public static Response<MassResult> massMsgByTag(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        MassResult mr;
        String requestUrl = MESSAGE_MASS_SENDALL_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            if (errCode == 0) {
                mr = new MassResult();
                Integer msgId = jsonObject.getInteger("msg_id");
                Long msgDataId = jsonObject.getLong("msg_data_id");
                mr.setMsgId(msgId);
                mr.setMsgDataId(msgDataId);
                response.success(errCode, errMsg, mr);
                log.info("标签群发图文消息成功 msg_id:{} ", msgId);
            } else {
                response.fail(errCode, errMsg);
                log.error("标签群发图文消息失败 errcode:{} errmsg:{}", errCode, errMsg);
            }
        }

        return response;
    }

    /**
     * 根据OpenID的群发消息【订阅号不可用，服务号认证后可用】
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return 群发结果
     */
    @SuppressWarnings({"unchecked"})
    public static Response<MassResult> massMsgByOpenID(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        MassResult mr;
        String requestUrl = MESSAGE_MASS_SEND_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            mr = new MassResult();
            if (errCode == 0) {
                Integer msgId = jsonObject.getInteger("msg_id");
                Long msgDataId = jsonObject.getLong("msg_data_id");
                mr.setMsgDataId(msgDataId);
                response.success(errCode, errMsg, mr);
                log.info("OpenID群发图文消息成功 msgId:{}", msgId);
            } else {
                response.fail(errCode, errMsg);
                log.error("OpenID群发图文消息失败 errcode:{} errmsg:{}", errCode, errMsg);
            }
        }

        return response;
    }

    /**
     * 删除群发【订阅号与服务号认证后均可用】
     * 已经发送成功的消息才能删除,删除消息只是将消息的图文详情页失效,已经收到的用户，还是能在其本地看到消息卡片。
     * 另外，删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return 删除群发结果
     */
    public static Response deleteMassMsg(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        String requestUrl = MESSAGE_MASS_DELETE_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            if (errCode == 0) {
                response.success();
                log.info("群发图文消息成功 errcode:{} errmsg:{}");
            } else {
                response.fail(errCode, errMsg);
                log.error("群发图文消息失败 errcode:{} errmsg:{}", errCode,
                        errMsg);
            }
        }

        return response;
    }

    /**
     * 群发预览接口【订阅号与服务号认证后均可用】
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return 群发预览
     */
    @SuppressWarnings({"unchecked"})
    public static Response<MassResult> massMsgPreview(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        MassResult mr;
        String requestUrl = MESSAGE_MASS_PREVIEW_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
        if (null != jsonObject) {
            int errCode = jsonObject.getIntValue(Constants.ERRCODE);
            String errMsg = jsonObject.getString(Constants.ERRMSG);
            mr = new MassResult();
            if (errCode == 0) {
                // TODO 并没有msg_id，是不是接口返回有问题(与文档说明不一致)
                Integer msgId = jsonObject.getInteger("msg_id");
                mr.setMsgId(msgId);
                response.success(errCode, errMsg, mr);
                log.info("预览群发图文消息成功");
            } else {
                response.fail(errCode, errMsg);
                log.error("预览群发图文消息失败 errcode:{} errmsg:{}", errCode,
                        errMsg);
            }
        }

        return response;
    }

    /**
     * 查询群发消息发送状态【订阅号与服务号认证后均可用】
     * @param accessToken 调用凭据
     * @param jsonMsg 消息体
     * @return 群发消息结果
     */
    @SuppressWarnings({"unchecked"})
    public static Response<MassResult> queryMassMsgStatus(String accessToken, String jsonMsg) {
        Response response = ResponseFactory.getInstance();
        MassResult mr;
        String requestUrl = MESSAGE_MASS_GET_URL.replace(Constants.ACCESS_TOKEN, accessToken);
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, Constants.HTTP_POST, jsonMsg);
        if (null != jsonObject) {
            int msgId  = jsonObject.getIntValue("msg_id");
            String msgStatus = jsonObject.getString("msg_status");
            mr = new MassResult();
            mr.setMsgId(msgId);
            mr.setMsgStatus(msgStatus);
            response.success(mr);
        }

        return response;
    }

    // TODO https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1481187827_i0l21
    // 控制群发速度
}
