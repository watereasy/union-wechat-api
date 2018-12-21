package com.jd.union.wechat.api.shake.resp;

import com.jd.union.wechat.api.shake.Page;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询页面列表(响应)
 * @author zhangjianhui
 *
 */
public class PageSearchResp {

	@Getter @Setter private List<Page> pages;

	@Getter @Setter private int total_count;

}
