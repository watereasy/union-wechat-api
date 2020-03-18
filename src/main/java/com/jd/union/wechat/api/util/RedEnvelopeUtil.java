package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.jd.union.wechat.api.model.RedEnvelope;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * 发红包
 * @author zhangjianhui
 *
 */
public class RedEnvelopeUtil {
	/** 过期时间 */
	private static final long INTERVAL_TIME = 3*24*60*60*1000;
	/** 存取证书 */
	private static Map<String, KeyStore> certFileMap = new HashMap<String, KeyStore>();
	/** 证书时效 */
	private static Map<String, Long> certFileTimeMap = new HashMap<String, Long>();
	/** 普通红包 */
	private static final String COMM_RED_ENVELOPE = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	/** 分裂红包 */
	private static final String GROUP_RED_ENVELOPE = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendgroupredpack";
	
	public static String sendComm(String password, String certFile, Map<String, Object> params) throws Exception {
		KeyStore keyStore;
		if(certFileMap.containsKey(certFile) && certFileTimeMap.containsKey(certFile)){
			long saveTime = certFileTimeMap.get(certFile);
			if(System.currentTimeMillis() > saveTime + INTERVAL_TIME){
				keyStore = getKeystore(certFile, password);
			}else{
				keyStore = certFileMap.get(certFile);
			}
		}else{
			keyStore = getKeystore(certFile, password);
		}

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, password.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        StringBuffer buffer = new StringBuffer();
        try {

        	String url = params.get("amt_type")==null?COMM_RED_ENVELOPE:GROUP_RED_ENVELOPE;
            HttpPost post = new HttpPost(url);
            String body = getBody(params); // 发送内容
            if(body.startsWith("err")){
            	return body;
            }
            //String mimeType = "";
            String charset = "UTF-8";
            Integer connTimeout = 3*1000;
            Integer readTimeout = 5*1000;
            
            if (StringUtils.isNotBlank(body)) {
				//HttpEntity entity = new StringEntity(body, ContentType.create(mimeType, charset));
				HttpEntity entity = new StringEntity(body, charset);
				post.setEntity(entity);
			}
			// 设置参数
			Builder customReqConf = RequestConfig.custom();
            customReqConf.setConnectTimeout(connTimeout);
            customReqConf.setSocketTimeout(readTimeout);
			post.setConfig(customReqConf.build());

            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                    	buffer.append(text);
                    }
                   
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return buffer.toString();
    }
	
	public static String getBody(Map<String, Object> params) throws Exception{
		String msg = check(params);
		if(StringUtils.isNotBlank(msg)){
			return msg;
		}
        String nonce_str = (String)params.get("nonce_str");
        String mch_id = (String)params.get("mch_id");
        String mch_billno = (String)params.get("mch_billno");
        String wxappid = (String)params.get("wxappid");
        String send_name = (String)params.get("send_name");
        String re_openid = (String)params.get("re_openid");
        int total_amount = (Integer)params.get("total_amount");
        int total_num = (Integer)params.get("total_num");
        String amt_type = (String)params.get("amt_type");
        String wishing = (String)params.get("wishing");
        String client_ip = (String)params.get("client_ip");
        String act_name = (String)params.get("act_name");
        String remark = (String)params.get("remark");
        String scene_id = (String)params.get("scene_id");
        String risk_info = (String)params.get("risk_info");
        String consume_mch_id = (String)params.get("consume_mch_id");
        String sign = null;
        String apikey = (String)params.get("apikey");;
        RedEnvelope red = new RedEnvelope();
        red.setNonce_str(nonce_str);
        red.setSign(sign);
        red.setMch_billno(mch_billno);
        red.setMch_id(mch_id);
        red.setWxappid(wxappid);
        red.setSend_name(send_name);
        red.setRe_openid(re_openid);
        red.setTotal_amount(total_amount);
        red.setTotal_num(total_num);
        red.setAmt_type(amt_type);
        red.setWishing(wishing);
        red.setClient_ip(client_ip);
        red.setAct_name(act_name);
        red.setRemark(remark);
        red.setScene_id(scene_id);
        red.setRisk_info(risk_info);
        red.setConsume_mch_id(consume_mch_id);
        // 过滤空域
	    JSONObject jsonObj = JSONObject.parseObject(JSONObject.toJSONString(red), Feature.OrderedField);
	    StringBuilder strBuil = new StringBuilder();
	    // 拼接字符串
        String value;
	    for(String key : jsonObj.keySet()){
	    	value = jsonObj.getString(key);
	    	strBuil.append(key).append("=").append(value).append("&");
	    }
	    // 拼接apikey
	    strBuil.append("key=").append(apikey);
	    // 生成MD5
        sign = MD5Util.getMD5(strBuil.toString());
        red.setSign(sign);
        String responseXml = MsgUtil.msgToXml(red);
        
        return responseXml;
    }
	
	private static String check(Map<String, Object> params){
		String msg = "";
		String nonce_str = (String)params.get("nonce_str");
        String mch_id = (String)params.get("mch_id");
        String mch_billno = (String)params.get("mch_billno");
        String wxappid = (String)params.get("wxappid");
        String send_name = (String)params.get("send_name");
        String re_openid = (String)params.get("re_openid");
        Integer total_amount = (Integer)params.get("total_amount");
        Integer total_num = (Integer)params.get("total_num");
        String amt_type = (String)params.get("amt_type");
        String wishing = (String)params.get("wishing");
        String client_ip = (String)params.get("client_ip");
        String act_name = (String)params.get("act_name");
        String remark = (String)params.get("remark");
        String scene_id = (String)params.get("scene_id");
        String risk_info = (String)params.get("risk_info");
        String consume_mch_id = (String)params.get("consume_mch_id");
        if(StringUtils.isBlank(nonce_str)){
        	msg = "err随机字符串为空";
        	return msg;
        }else if(CommonUtil.length(nonce_str) > 32){
        	msg = "err随机字符串，应不长于32位";
        	return msg;
        }
        if(StringUtils.isBlank(mch_id)){
        	msg = "err商户号为空";
        	return msg;
        }
        if(StringUtils.isBlank(mch_billno)){
        	msg = "err商户订单号为空";
        	return msg;
        }else if(CommonUtil.length(mch_billno) > 28){
        	msg = "err商户订单号，应不长于28位";
        	return msg;
        }
        if(StringUtils.isBlank(wxappid)){
        	msg = "err公众账号appid为空";
        	return msg;
        }else if(CommonUtil.length(wxappid) > 32){
        	msg = "err公众账号appid，应不长于32位";
        	return msg;
        }
        if(StringUtils.isBlank(send_name)){
        	msg = "err商户名称为空";
        	return msg;
        }else if(CommonUtil.length(send_name) > 32){
        	msg = "err商户名称，应不长于32位";
        	return msg;
        }
        if(StringUtils.isBlank(re_openid)){
        	msg = "err用户openid为空";
        	return msg;
        }else if(CommonUtil.length(re_openid) > 32){
        	msg = "err用户openid，应不长于32位";
        	return msg;
        }
        if(total_amount == null){
        	msg = "err付款金额为空";
        	return msg;
        }else if(total_amount < 0){
        	msg = "err付款金额为负";
        	return msg;
        }
        if(total_num == null){
        	msg = "err红包发放总人数为空";
        	return msg;
        }else if(StringUtils.isBlank(amt_type) && total_num != 1){
        	msg = "err红包发放总人数不为1";
        	return msg;
        }
        if(StringUtils.isBlank(wishing)){
        	msg = "err红包祝福语为空";
        	return msg;
        }else if(CommonUtil.length(wishing) > 128){
        	msg = "err红包祝福语，应不长于128位";
        	return msg;
        }
        if(StringUtils.isBlank(client_ip)){
        	msg = "errIp地址为空";
        	return msg;
        }else if(CommonUtil.length(client_ip) > 15){
        	msg = "errIp地址，应不长于15位";
        	return msg;
        }
        if(StringUtils.isBlank(act_name)){
        	msg = "err活动名称为空";
        	return msg;
        }else if(CommonUtil.length(act_name) > 32){
        	msg = "err活动名称，应不长于32位";
        	return msg;
        }
        if(StringUtils.isBlank(remark)){
        	msg = "err备注为空";
        	return msg;
        }else if(CommonUtil.length(remark) > 256){
        	msg = "err备注，应不长于256位";
        	return msg;
        }
        if(StringUtils.isNotBlank(scene_id) || CommonUtil.length(scene_id) > 32){
        	msg = "err场景id，应不长于32位";
        	return msg;
        }
        if(StringUtils.isNotBlank(risk_info) || CommonUtil.length(risk_info) > 128){
        	msg = "err活动信息，应不长于128位";
        	return msg;
        }
        if(StringUtils.isNotBlank(consume_mch_id) || CommonUtil.length(consume_mch_id) > 32){
        	msg = "err资金授权商户号，应不长于32位";
        	return msg;
        }
		return msg;
	}
	
	/**
	 * 生成KeyStore并缓存
	 * @param certFile 验证文件路径
	 * @param password 密码
	 * @return KeyStore
	 * @throws Exception 异常
	 */
	private static KeyStore getKeystore(String certFile, String password) throws Exception{
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(certFile));
		try {
			keyStore.load(instream, password.toCharArray());
			certFileMap.put(certFile, keyStore);
			certFileTimeMap.put(certFile, System.currentTimeMillis());
		} finally {
			instream.close();
		}
		
		return keyStore;
	}
}
