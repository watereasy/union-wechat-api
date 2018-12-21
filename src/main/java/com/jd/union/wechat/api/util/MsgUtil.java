package com.jd.union.wechat.api.util;

import com.jd.union.wechat.api.message.resp.Article;
import com.jd.union.wechat.api.message.resp.Image;
import com.jd.union.wechat.api.message.resp.ImageMsg;
import com.jd.union.wechat.api.message.resp.MusicMsg;
import com.jd.union.wechat.api.message.resp.NewsMsg;
import com.jd.union.wechat.api.message.resp.TextMsg;
import com.jd.union.wechat.api.message.resp.VideoMsg;
import com.jd.union.wechat.api.message.resp.VoiceMsg;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息处理工具类
 *
 */
public class MsgUtil {
	/** 请求消息类型 */
	// 请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	// 请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	// 请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	// 请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	// 请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	// 请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";
	// 请求消息类型：事件推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	
	/** 事件类型 */
	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：scan(用户已关注时的扫描带参数二维码)
	public static final String EVENT_TYPE_SCAN = "scan";
	// 事件类型：LOCATION(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(自定义菜单)
	public static final String EVENT_TYPE_CLICK = "CLICK";
	// 事件类型：MASSSENDJOBFINISH(事件推送群发结果)
	public static final String EVENT_TYPE_MASSSENDJOBFINISH = "MASSSENDJOBFINISH";
	// 事件类型：TEMPLATESENDJOBFINISH(模版消息发送任务)
	public static final String EVENT_TYPE_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";
	
	/** 响应消息类型 */
	// 响应消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// 响应消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// 响应消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// 响应消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// 响应消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// 响应消息类型：图文
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	/** 群发消息类型 */
	// 群发消息类型：文本
	public static final String MASS_MESSAGE_TYPE_TEXT = "text";
	// 群发消息类型：图片
	public static final String MASS_MESSAGE_TYPE_IMAGE = "image";
	// 群发消息类型：语音
	public static final String MASS_MESSAGE_TYPE_VOICE = "voice";
	// 群发消息类型：视频
	public static final String MASS_MESSAGE_TYPE_VIDEO = "mpvideo";
	// 群发消息类型：图文
	public static final String MASS_MESSAGE_TYPE_NEWS = "mpnews";
	// 群发消息类型：卡券
	public static final String MASS_MESSAGE_TYPE_CARD = "wxcard";
	
	/**
	 * 解析微信发来的请求(XML)
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(InputStream is) throws Exception {
		// 将解析的结果存储到Map中
		Map<String, String> map = new HashMap<String, String>();
		// 从request中取得输入流
//		InputStream is = request.getInputStream();
		SAXReader reader = new SAXReader();
		// 读取输入流
		Document document = reader.read(is);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementlist = root.elements();
		// 遍历所有子节点
		for(Element e : elementlist){
			map.put(e.getName(), e.getText());
		}
		//释放资源
		is.close();
		is = null;
		
		return map;
	}
	
	public static Map<String, String> parseXml(String xml) throws Exception {
		// 将解析的结果存储到Map中
		Map<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xml);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementlist = root.elements();
		// 遍历所有子节点
		for(Element e : elementlist){
			map.put(e.getName(), e.getText());
		}
		
		return map;
	}
	
	/**
	 * 扩展xstream使其支持CDATA
	 */
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out, new XStreamNameCoder()) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				
				@SuppressWarnings("rawtypes")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}
				
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
				
			};
		}
	});
	
	public static String msgToXml(Object object) {
		xstream.alias("xml", object.getClass());
		return xstream.toXML(object);
	}
	
	/**
	 * 文本消息对象转换成xml
	 * 
	 * @param textMsg 文本消息对象
	 * @return xml
	 */
	public static String msgToXml(TextMsg textMsg) {
		xstream.alias("xml", textMsg.getClass());
		return xstream.toXML(textMsg);
	}
	
	/**
	 * 图片消息对象转换成xml
	 * 
	 * @param imageMsg 图片消息对象
	 * @return xml
	 */
	public static String msgToXml(ImageMsg imageMsg) {
		xstream.alias("xml", imageMsg.getClass());
		return xstream.toXML(imageMsg);
	}
	
	/**
	 * 语音消息对象转换成xml
	 * 
	 * @param voiceMsg 语音消息对象
	 * @return xml
	 */
	public static String msgToXml(VoiceMsg voiceMsg) {
		xstream.alias("xml", voiceMsg.getClass());
		return xstream.toXML(voiceMsg);
	}
	
	/**
	 * 视频消息对象转换成xml
	 * 
	 * @param videoMsg 视频消息对象
	 * @return xml
	 */
	public static String msgToXml(VideoMsg videoMsg) {
		xstream.alias("xml", videoMsg.getClass());
		return xstream.toXML(videoMsg);
	}
	
	/**
	 * 音乐消息对象转换成xml
	 * 
	 * @param musicMsg 音乐消息对象
	 * @return xml
	 */
	public static String msgToXml(MusicMsg musicMsg) {
		xstream.alias("xml", musicMsg.getClass());
		return xstream.toXML(musicMsg);
	}
	
	/**
	 * 图文消息对象转换成xml
	 * 
	 * @param newsMsg 图文消息对象
	 * @return xml
	 */
	public static String msgToXml(NewsMsg newsMsg) {
		xstream.alias("xml", newsMsg.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(newsMsg);
	}
	
	/**
	 * 单元测试
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		ImageMsg imageMsg = new ImageMsg();
		imageMsg.setToUserName("to");
		imageMsg.setFromUserName("from");
		imageMsg.setCreateTime(new java.util.Date().getTime());
		imageMsg.setMsgType(RESP_MESSAGE_TYPE_IMAGE);
		Image image = new Image();
		image.setMediaId("1111111111111111");
		imageMsg.setImage(image);
		System.out.println(msgToXml(imageMsg));
		System.out.println("==================");
		String xmlStr = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[发放成功]]></return_msg><result_code><![CDATA[SUCCESS]]></result_code><err_code><![CDATA[SUCCESS]]></err_code><err_code_des><![CDATA[发放成功]]></err_code_des><mch_billno><![CDATA[13825888021474885891441SyiD]]></mch_billno><mch_id><![CDATA[1382588802]]></mch_id><wxappid><![CDATA[wx63457c110481fdf2]]></wxappid><re_openid><![CDATA[oR8rRvhSBAkn07aeeEmtTAg_ob8w]]></re_openid><total_amount>100</total_amount><send_listid><![CDATA[1000041701201609263000066770003]]></send_listid></xml>";
		System.out.println(parseXml(xmlStr));
	}
}
