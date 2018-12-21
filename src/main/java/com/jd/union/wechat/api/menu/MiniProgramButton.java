package com.jd.union.wechat.api.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 小程序按钮
 * Created by zhangjianhui6 on 2018/12/13.
 *
 **/
@ToString(callSuper=true)
public class MiniProgramButton extends Button{

    @Getter @Setter private String type;
    @Getter @Setter private String url;
    @Getter @Setter private String appid;
    @Getter @Setter private String pagepath;
}
