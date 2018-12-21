package com.jd.union.wechat.api.util;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerTest {

    @Test
    public void test(){
        log.info("test");
        log.warn("test");
        log.error("test");
    }
}
