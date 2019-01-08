package com.jd.union.wechat.api.util;

import com.jd.union.wechat.api.message.resp.Image;
import com.jd.union.wechat.api.message.resp.ImageMsg;
import com.jd.union.wechat.api.message.resp.TextMsg;

import common.InitUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.util.Date;

public class MsgUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        InitUtil.initAccessToken();
    }

    @Test
    public void testMsgToXml() throws Exception{
        ImageMsg imageMsg = new ImageMsg();
        imageMsg.setToUserName("to");
        imageMsg.setFromUserName("from");
        imageMsg.setCreateTime(new java.util.Date().getTime());
        imageMsg.setMsgType(MsgUtil.RESP_MESSAGE_TYPE_IMAGE);
        Image image = new Image();
        image.setMediaId("1111111111111111");
        imageMsg.setImage(image);
        System.out.println(MsgUtil.msgToXml(imageMsg));
        System.out.println("==================");
        String xmlStr = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[发放成功]]></return_msg><result_code><![CDATA[SUCCESS]]></result_code><err_code><![CDATA[SUCCESS]]></err_code><err_code_des><![CDATA[发放成功]]></err_code_des><mch_billno><![CDATA[13825888021474885891441SyiD]]></mch_billno><mch_id><![CDATA[1382588802]]></mch_id><wxappid><![CDATA[wx63457c110481fdf2]]></wxappid><re_openid><![CDATA[oR8rRvhSBAkn07aeeEmtTAg_ob8w]]></re_openid><total_amount>100</total_amount><send_listid><![CDATA[1000041701201609263000066770003]]></send_listid></xml>";
        System.out.println(MsgUtil.parseXml(xmlStr));
    }

    @Test
    public void testTextMsg(){
        // 发送方帐号
        String fromUserName = "111";
        // 开发者微信号
        String toUserName = "222";
        TextMsg textMsg = new TextMsg();
        textMsg.setToUserName(fromUserName);
        textMsg.setFromUserName(toUserName);
        textMsg.setCreateTime(new Date().getTime());
        // 设置文本消息的内容
        String respContent = "";
        textMsg.setContent(respContent);
        // 将文本消息对象转换成xml
        String  respXml = MsgUtil.msgToXml(textMsg);
    }
}
