/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.checkcode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 类TMDom4JUtil.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-11-2 下午1:44:13
 */
public class TMDom4JUtil {
	private static final String NODE_SPLIT = ".";
	private static final String NODE_ATTRIBUTE = "name";

	public void createXml(String fileName) {
		Document document = DocumentHelper.createDocument();
		Element employees = document.addElement("employees");
		Element employee = employees.addElement("employee");
		Element name = employee.addElement("name");
		name.setText("ddvip");
		Element sex = employee.addElement("sex");
		sex.setText("m");
		Element age = employee.addElement("age");
		age.setText("29");
		try {
			Writer fileWriter = new FileWriter(fileName);
			XMLWriter xmlWriter = new XMLWriter(fileWriter);
			xmlWriter.write(document);
			xmlWriter.close();
		} catch (IOException e) {

			System.out.println(e.getMessage());
		}

	}

	public static Map<String, TMStringKeyProperty> parserXml(String fileName) {
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		Map<String, TMStringKeyProperty> map = new HashMap<String, TMStringKeyProperty>();
		try {
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			for (Iterator i = employees.elementIterator(); i.hasNext();) {
				Element employee = (Element) i.next();
				TMStringKeyProperty p = new TMStringKeyProperty();
				// p.setFileName(fileName);
				p.setFullPath(fileName);
				p.setType(employee.getName());
				p.setValue(employee.getTextTrim());
				String name = employee.attributeValue(NODE_ATTRIBUTE);
				if (name != null && name.trim() != "") {
					map.put(name, p);
				}
				for (Iterator j = employee.elementIterator(); j.hasNext();) {
					Element node = (Element) j.next();
					map.put(name + NODE_SPLIT
							+ node.attributeValue(NODE_ATTRIBUTE), p);
				}

			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) {
		Map<String, TMStringKeyProperty> p = parserXml("D:\\test_comment_strings.xml");
		for (Entry<String, TMStringKeyProperty> entry : p.entrySet()) {
			System.out.println(entry.getKey());
		}
	}
}
