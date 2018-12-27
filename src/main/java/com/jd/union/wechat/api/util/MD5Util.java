package com.jd.union.wechat.api.util;

import java.security.MessageDigest;

public class MD5Util {
	/**
	 * 生成md5
	 * @param message 源字符串
	 * @return md5值
	 */
	public static String getMD5(String message) {
		String md5str = "";
		try {
			//1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
			MessageDigest md = MessageDigest.getInstance("MD5");

			//2 将消息变成byte数组
			byte[] input = message.getBytes("utf-8");

			//3 计算后获得字节数组,这就是那128位了
			byte[] buff = md.digest(input);

			//4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
			md5str = bytesToHex(buff);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return md5str;
	}

	/**
	 * 二进制转十六进制
	 * @param bytes 字节
	 * @return String
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuilder md5str = new StringBuilder();
		// 把数组每一字节换成16进制连成md5字符串
		int digital;
		for (byte b : bytes) {
			digital = b;
			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		
		return md5str.toString().toUpperCase();
	}
	
	public static void main(String[] args) {
		String result = getMD5("appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA&key=192006250b4c09247ec02edce69f6a2d");
		System.out.println(result);
	}
}
