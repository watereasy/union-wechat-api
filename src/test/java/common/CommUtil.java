package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;


public class CommUtil {
	/** 不要和Properties混用 */
	private static ResourceBundle bun = ResourceBundle.getBundle("config");
	private static Properties pps = new Properties();
	private static String path = "D:\\project\\jd\\union-wechat-api\\target\\test-classes\\config.properties";
	
	static {
		try {
			pps.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getBundle(String key){
		return bun.getString(key);
	}
	
	public static String getProperties(String key) throws Exception{
		return pps.getProperty(key);
	}
	
	public static void setProperties(String key, String value) throws Exception {
		pps.setProperty(key, value);
		pps.store(new FileOutputStream(path), "Update " + key + " name");
	}

	public static void main(String[] args) throws Exception{
		System.out.println(CommUtil.getProperties(Constant.CREATE_TIME));
	}
}
