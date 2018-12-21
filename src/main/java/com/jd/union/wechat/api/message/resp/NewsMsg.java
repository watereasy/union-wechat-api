package com.jd.union.wechat.api.message.resp;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 回复图文消息
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper = true)
public class NewsMsg extends BaseMsg {
	// 图文消息个数，限制为10条以内
	@Getter @Setter private int ArticleCount;
	// 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	@Getter @Setter private List<Article> Articles;

}
