package com.jd.union.wechat.api.shake.req;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询页面列表(请求)
 * @author zhangjianhui
 *
 */
public class PageSearchReq {

	@Getter @Setter private String type;

	@Getter @Setter private List<Integer> page_ids;

	@Getter @Setter private int begin;

	@Getter @Setter private int count;

}
