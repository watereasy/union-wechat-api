package com.jd.union.wechat.api.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * Created by zhangjianhui6 on 2018/12/6.
 * 
 **/
@ToString
public class WeixinPOI {
	// 商户自己的id，用于后续审核通过收到poi_id 的通知时，做对应关系。请商户自己保证唯一识别性
	@Getter @Setter private String sid;
	// 门店名称（仅为商户名，如：国美、麦当劳，不应包含地区、地址、分店名等信息，错误示例：北京国美）
	@Getter @Setter private String business_name;
	// 分店名称（不应包含地区信息，不应与门店名有重复，错误示例：北京王府井店）
	@Getter @Setter private String branch_name;
	// 门店所在的省份（直辖市填城市名,如：北京市）
	@Getter @Setter private String province;
	// 门店所在的城市
	@Getter @Setter private String city;
	// 门店所在地区
	@Getter @Setter private String district;
	// 门店所在的详细街道地址（不要填写省市信息）
	@Getter @Setter private String address;
	// 门店的电话（纯数字，区号、分机号均由“-”隔开）
	@Getter @Setter private String telephone;
	// 门店的类型（不同级分类用“,”隔开，如：美食，川菜，火锅。详细分类参见附件：微信门店类目表）
	@Getter @Setter private List<String> categories;
	// 坐标类型，1 为火星坐标（目前只能选1）
	@Getter @Setter private int offset_type;
	// 门店所在地理位置的经度
	@Getter @Setter private double longitude;
	// 门店所在地理位置的纬度（经纬度均为火星坐标，最好选用腾讯地图标记的坐标）
	@Getter @Setter private double latitude;
	// 图片列表
	@Getter @Setter private List<WeixinPhoto> photo_list;
	// 推荐品
	@Getter @Setter private String recommend;
	// 特色服务
	@Getter @Setter private String special;
	// 商户简介
	@Getter @Setter private String introduction;
	// 营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
	@Getter @Setter private String open_time;
	// 人均价格，大于0 的整数
	@Getter @Setter private int avg_price;
	// 门店是否可用状态。1 表示系统错误、2 表示审核中、3 审核通过、4 审核驳回。当该字段为1、2、4 状态时，poi_id 为空
	@Getter @Setter private int available_state;
	// 扩展字段是否正在更新中。1 表示扩展字段正在更新中，尚未生效，不允许再次更新； 0 表示扩展字段没有在更新中或更新已生效，可以再次更新
	@Getter @Setter private int update_status;
	// 最终id，审核通过后推送获取
	@Getter @Setter private String poi_id;

}
