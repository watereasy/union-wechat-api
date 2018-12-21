package com.jd.union.wechat.api.card;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 卡券基类
 * 
 * @author zhangjianhui
 * 
 */
@ToString
public class Card {
	// 卡券的商户logo，建议像素为300*300
	@Getter @Setter private String logo_url;
	// Code展示类型，"CODE_TYPE_TEXT"，文本；"CODE_TYPE_BARCODE"，一维码
	// ；"CODE_TYPE_QRCODE"，二维码；
	// "CODE_TYPE_ONLY_QRCODE",二维码无code显示；"CODE_TYPE_ONLY_BARCODE",一维码无code显示
	@Getter @Setter private String code_type;
	// 商户名字,字数上限为12个汉字
	@Getter @Setter private String brand_name;
	// 卡券名，字数上限为9个汉字。(建议涵盖卡券属性、服务及金额)
	@Getter @Setter private String title;
	// 券名，字数上限为18个汉字
	@Getter @Setter private String sub_title;
	// 券颜色
	@Getter @Setter private String color;
	// 卡券使用提醒，字数上限为16个汉字
	@Getter @Setter private String notice;
	// 客服电话
	@Getter @Setter private String service_phone;
	// 卡券使用说明，字数上限为1024个汉字
	@Getter @Setter private String description;
	// 使用日期，有效期的信息
	@Getter @Setter private DateInfo date_info;
	// 商品信息
	@Getter @Setter private Sku sku;
	// 每人可领券的数量限制,不填写默认为50
	@Getter @Setter private int get_limit;
	// 是否自定义Code码。填写true或false，默认为false
	@Getter @Setter private boolean use_custom_code;
	// 是否指定用户领取，填写true或false。默认为false。通常指定特殊用户群体投放卡券或防止刷券时选择指定用户领取
	@Getter @Setter private boolean bind_openid;
	// 卡券领取页面是否可分享
	@Getter @Setter private boolean can_share;
	// 卡券是否可转赠
	@Getter @Setter private boolean can_give_friend;
	// 门店位置ID
	@Getter @Setter private List<Integer> location_id_list;
	// 自定义跳转外链的入口名字
	@Getter @Setter private String custom_url_name;
	// 自定义跳转的URL
	@Getter @Setter private String custom_url;
	// 显示在入口右侧的提示语
	@Getter @Setter private String custom_url_sub_title;
	// 营销场景的自定义入口名称
	@Getter @Setter private String promotion_url_name;
	// 入口跳转外链的地址链接
	@Getter @Setter private String promotion_url;
	// 第三方来源名，例如同程旅游、大众点评
	@Getter @Setter private String source;

}
