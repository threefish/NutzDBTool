package com.bean;

/**
 * 实体对应的属性类
 * @author  lvzb.software@qq.com
 *
 */
public class Property {
	// 属性数据类型
	private String javaType;
	// 属性名称
	private String propertyName;
	//字段名次
	private String columName;
	//描述
	private String desc=null;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getColumName() {
		return columName;
	}

	public void setColumName(String columName) {
		this.columName = columName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}


}
