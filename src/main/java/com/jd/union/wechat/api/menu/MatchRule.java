package com.jd.union.wechat.api.menu;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 	matchrule共六个字段，均可为空，但不能全部为空，至少要有一个匹配信息是不为空的。
 *  country、province、city组成地区信息，将按照country、province、city的顺序进行验证，要符合地区信息表的内容。
 *  地区信息从大到小验证，小的可以不填，即若填写了省份信息，则国家信息也必填并且匹配，城市信息可以不填。 
 *  例如 “中国 广东省 广州市”、“中国 广东省”都是合法的地域信息，而“中国 广州市”则不合法，因为填写了城市信息但没有填写省份信息。
 * Created by zhangjianhui6 on 2018/12/6.
 *
 */
@ToString
public class MatchRule {
	/** 用户标签的id，可通过用户标签管理接口获取 */
	@Getter @Setter private String tag_id;
	/** 性别：男（1）女（2），不填则不做匹配 */
	@Getter @Setter private String sex;
	/** 国家信息，是用户在微信中设置的地区，具体请参考地区信息表 */
	@Getter @Setter private String country;
	/** 省份信息，是用户在微信中设置的地区，具体请参考地区信息表 */
	@Getter @Setter private String province;
	/** 城市信息，是用户在微信中设置的地区，具体请参考地区信息表 */
	@Getter @Setter private String city;
	/** 客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配 */
	@Getter @Setter private String client_platform_type;
	/** 语言信息，是用户在微信中设置的语言，具体请参考语言表 */
	@Getter @Setter private String language;

}
