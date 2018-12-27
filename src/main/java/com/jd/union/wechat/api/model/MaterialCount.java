package com.jd.union.wechat.api.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MaterialCount implements Serializable{
    private static final long serialVersionUID = 7575619177847639844L;
    // 语音总数量
    @Getter @Setter private Long voiceCount;
    // 视频总数量
    @Getter @Setter private Long videoCount;
    // 图片总数量
    @Getter @Setter private Long imageCount;
    // 图文总数量
    @Getter @Setter private Long newsCount;
}
