package com.jd.union.wechat.api.card;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品信息
 * @author zhangjianhui
 *
 */
@ToString
public class Sku {
	// 卡券库存的数量，不支持填写0，上限为100000000
	@Getter @Setter private int quantity;

}
