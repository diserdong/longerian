/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.checkcode;

/**
 * 类TMResultProterty.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-10-31 下午9:29:17
 */
public class TMResultProperty {
	private TMStringKeyProperty baseProperty;// 基本组工程的属性
	private TMStringKeyProperty newProperty;// 新子工程的属性

	public TMResultProperty() {

	}

	public TMResultProperty(TMStringKeyProperty baseProperty,
			TMStringKeyProperty newProperty) {
		this.setBaseProperty(baseProperty);
		this.newProperty = newProperty;
	}

	/**
	 * @return the newProperty
	 */
	public TMStringKeyProperty getNewProperty() {
		return newProperty;
	}

	/**
	 * @param newProperty
	 *            the newProperty to set
	 */
	public void setNewProperty(TMStringKeyProperty newProperty) {
		this.newProperty = newProperty;
	}

	/**
	 * @return the baseProperty
	 */
	public TMStringKeyProperty getBaseProperty() {
		return baseProperty;
	}

	/**
	 * @param baseProperty
	 *            the baseProperty to set
	 */
	public void setBaseProperty(TMStringKeyProperty baseProperty) {
		this.baseProperty = baseProperty;
	}
}
