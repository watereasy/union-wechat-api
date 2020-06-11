package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jd.union.wechat.api.base.Response;
import com.jd.union.wechat.api.menu.Button;
import com.jd.union.wechat.api.menu.ClickButton;
import com.jd.union.wechat.api.menu.ComplexButton;
import com.jd.union.wechat.api.menu.ConditionalMenu;
import com.jd.union.wechat.api.menu.MatchRule;
import com.jd.union.wechat.api.menu.Menu;
import com.jd.union.wechat.api.menu.MiniProgramButton;
import com.jd.union.wechat.api.menu.OtherButton;
import com.jd.union.wechat.api.menu.ViewButton;

import common.InitUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class Menu4UnionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
//		InitUtil.initAccessToken();
        appId = "wx8e109352663d4237";
        domainName = "uh5dev.jd.com";
        wechatOA = "union_dev";
        accessToken = "26_imUFIRqNMByJtYwBbqVUSCYcAPsZSVw39mrvmMwtv7DkuwVaVQV4JYjBo3v68efLf9SbBnmBAeCozif7ouw9b7o9RAF6IeVyb3zeHitUt6sCXEXAyvAuY1TfWWRpZ3ZAdi3x9omZYpKxbzx_SEHhAAAIUX";
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

    private static String appId;
    private static String domainName;
    private static String wechatOA;
    private static String accessToken;

	/**
	 * 京粉公众号-联盟研发专用
	 * @author zhangjianhui6
	 * @date 2018/12/12.
	 */
	@Test
	public void testCreateMenu2() {
		// 拼接菜单
		ViewButton btn31 = new ViewButton();
		btn31.setName("账号管理");
		btn31.setType("view");
		btn31.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId +"&redirect_uri=http%3a%2f%2f" + domainName + "%2fapi%2fh5%2fwechat%2fauth%3fwechatOA%3d"+ wechatOA +"%26to%3d%2fjingfenermp%2faccount&response_type=code&scope=snsapi_base&state=123#wechat_redirect");

        ViewButton btn32 = new ViewButton();
        btn32.setName("PID管理");
        btn32.setType("view");
        btn32.setUrl("http://uh5dev.jd.com/jingfenermp/newPid");

//            ViewButton btn32 = new ViewButton();
//            btn32.setName("佣金查看");
//            btn32.setType("view");
//            btn32.setUrl("http://www.jd.com");
		// TODO 需小程序和公众号绑定
		MiniProgramButton btn33 = new MiniProgramButton();
		btn33.setName("佣金查看");
		btn33.setType("miniprogram");
		btn33.setUrl("http://www.jd.com");
		btn33.setAppid("wxf463e50cd384beda");
		btn33.setPagepath("pages/commission/commission"); // 链接小程序佣金统计，直接呼起京粉精选小程序。
		ViewButton btn34 = new ViewButton();
		btn34.setName("活动公告");
		btn34.setType("view");
		btn34.setUrl("https://" + domainName + "/jingfenermp/announcement");

		ViewButton main1 = new ViewButton();
		main1.setName("我要赚钱");
		main1.setType("view");
		main1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId +"&redirect_uri=http%3a%2f%2f" + domainName + "%2fapi%2fh5%2fwechat%2fauth%3fwechatOA%3d"+ wechatOA +"%26to%3d%2fjingfenermp&response_type=code&scope=snsapi_base&state=123#wechat_redirect");

		ViewButton main2 = new ViewButton();
		main2.setName("京粉学堂");
		main2.setType("view");
		main2.setUrl("http://mp.weixin.qq.com/mp/homepage?__biz=MzAxOTc4NTE3NA==&hid=3&sn=87fd787357074e8a2d2fd78b03379448&scene=18#wechat_redirect");

		ComplexButton main3 = new ComplexButton();
		main3.setName("我的");
		main3.setSub_button(new Button[]{btn31, btn32, btn33, btn34});

		Menu menu = new Menu();
		menu.setButton(new Button[]{main1, main2, main3});
		System.out.println(JSONObject.toJSONString(menu));
		Response responseWechat = MenuUtil.createMenu(this.getAccessToken(wechatOA), JSONObject.toJSONString(menu));
		System.out.print("update menu response:" + JSON.toJSONString(responseWechat));
	}

	private String getAccessToken(String wechatOA){
	    return accessToken;
    }
	
}
