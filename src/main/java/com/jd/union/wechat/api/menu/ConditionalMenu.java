package com.jd.union.wechat.api.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 个性化菜单
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString(callSuper=true)
public class ConditionalMenu extends Menu {

	@Getter @Setter private MatchRule matchrule;

}
