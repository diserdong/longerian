/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.checkcode;

import java.io.Serializable;

/**
 * 类TMStringKeyProperty.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-10-31 下午8:54:27
 */
public class TMStringKeyProperty implements Serializable {

	private static final long serialVersionUID = -3884285002413433979L;
	private String fileName; // 文件名称
	private String fullPath; // 文件全路径,带文件名
	private String value; // 标准工程的id值
	private String type;

	public TMStringKeyProperty() {

	}

	public TMStringKeyProperty(String fileName, String fullPath, String value,
			String type) {
		this.fileName = fileName;
		this.fullPath = fullPath;
		this.value = value;
		this.type = type;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fullPath
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @param fullPath
	 *            the fullPath to set
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
