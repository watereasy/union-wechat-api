package com.jd.union.wechat.api.factory;

import com.jd.union.wechat.api.base.Response;

public class ResponseFactory {

    /** 获取默认 */
    public static Response getInstance(){
        Response response = new Response();
        return response;
    }
}
