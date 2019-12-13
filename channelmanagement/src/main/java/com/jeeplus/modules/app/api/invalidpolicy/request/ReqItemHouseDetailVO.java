package com.jeeplus.modules.app.api.invalidpolicy.request;

import java.io.Serializable;
/**
 * 房产信息vo
 * 
 * @author wangfz
 * @date 2018-2-24
 */
public class ReqItemHouseDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String structure; // 房屋结构 默认为空，与综合保持一致
	private String houseVariety; // 房产类别 默认为空，与综合保持一致
	private String buildArea; // 房屋面积 默认为空，与综合保持一致
	private String buildTime;// 修建年月 默认为空，与综合保持一致
	private String buildPost;// 保单号 默认为空，与综合保持一致
	private String buildAdress;// 邮政编码 默认为空，与综合保持一致
	private String unitValue;// 购买价格 试着默认为空，看看核心规则是否没有校验。

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getHouseVariety() {
		return houseVariety;
	}

	public void setHouseVariety(String houseVariety) {
		this.houseVariety = houseVariety;
	}

	public String getBuildArea() {
		return buildArea;
	}

	public void setBuildArea(String buildArea) {
		this.buildArea = buildArea;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

	public String getBuildPost() {
		return buildPost;
	}

	public void setBuildPost(String buildPost) {
		this.buildPost = buildPost;
	}

	public String getBuildAdress() {
		return buildAdress;
	}

	public void setBuildAdress(String buildAdress) {
		this.buildAdress = buildAdress;
	}

	public String getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ReqItemHouseDetailVO [structure=" + structure + ", houseVariety=" + houseVariety + ", buildArea="
				+ buildArea + ", buildTime=" + buildTime + ", buildPost=" + buildPost + ", buildAdress=" + buildAdress
				+ ", unitValue=" + unitValue + "]";
	}

}
