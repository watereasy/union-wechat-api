package com.jd.union.wechat.api.util;

import common.EncodeUtil;

import org.junit.Test;

public class Md5Test {

    @Test
    public void test()throws Exception{
        String content = "立即抢购: https://u.jd.com/y5DvJ5";
        content = new String(content.getBytes(),"utf-8");
        content = new String(content.getBytes(),"gb2312");
        System.out.println(EncodeUtil.getEncoding(content));

        String text = 1000185685L + "|" + content + "|" + "81305005-afb3-4c0e-93a2-aaa450cecc95";
        text = new String(text.getBytes("utf-8"),"utf-8");
        System.out.println(EncodeUtil.getEncoding(text));
        System.out.println(MD5Util.getMD5(text));
    }
}
