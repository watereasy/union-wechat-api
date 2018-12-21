package com.jd.union.wechat.api.model;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * Created by zhangjianhui6 on 2018/12/6.
 * 
 **/
@ToString
public class WeixinAction {

	@Getter @Setter private String action_name;

	@Getter @Setter private int expire_seconds;

	@Getter @Setter private JSONObject action_info;

}
