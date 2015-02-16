/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.checkcode;

/**
 * 类TMCheckTypeEnum.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-11-2 下午3:48:32
 */
public enum TMCheckTypeEnum {
	XML_DUPLICATE_DEFINITION(0), RES_FILE_NAME_DUPLICATION(1);
	private int value;

	TMCheckTypeEnum(int value) {
		this.setValue(value);
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
}
