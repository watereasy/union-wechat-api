package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.factory.ResponseFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageUtil {

    /** 添加客服账号(POST) */
    private static final String MESSAGE_CUSTOM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

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


    // TODO https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1481187827_i0l21
    // 控制群发速度
}
