package com.jd.union.wechat.api.shake.resp;

import com.jd.union.wechat.api.shake.BeaconInfo;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取摇周边的设备及用户信息(请求)
 * @author zhangjianhui
 *
 */
public class UserGetShakeInfoResp {

    @Getter @Setter private int page_id;

    @Getter @Setter private BeaconInfo beacon_info;

    @Getter @Setter private String openid;

    @Getter @Setter private int poi_id;
	
}
