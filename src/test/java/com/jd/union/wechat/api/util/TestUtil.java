package com.jd.union.wechat.api.util;

import com.alibaba.fastjson.JSON;
import com.jd.union.wechat.api.menu.ClickButton;
import com.jd.union.wechat.api.menu.MatchRule;

import org.junit.Test;

import lombok.ToString;

public class TestUtil {

    @Test
    public void test(){
        System.out.println(new MatchRule().toString());
        System.out.println(new ClickButton().toString());
        System.out.println(JSON.toJSONString(new ClickButton()));
    }

    @Test
    public void test02(){
        System.out.println(System.currentTimeMillis());
    }
}
