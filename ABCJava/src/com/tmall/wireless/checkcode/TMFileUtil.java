/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.checkcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 类TMFileUtil.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-10-31 下午8:36:16
 */
public class TMFileUtil {
	private static String RESULT_FILE_NAME = "TMCheckCodeResult_";
	private static String REUSLT_DEFAULT_PATH = "D:\\";
	private static String SCAN_DEFAULT_FILE_TYPE = ".xml";
	private static final String DIRECTORY_RESOURCE = "res";// 扫描目录
	private static String[] EXECLUSION_XML_FILES = { "pom.xml",
			"AndroidManifest.xml" };

	public static void mkdir(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public static void creatFile(String path) {
		File file = new File(path);
		// 如果文件存在，则先删除
		if (file.exists()) {
			delete(path);
		}
		if (!file.exists()) {
			if (file.getParentFile() != null) {
				file.getParentFile().mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("--创建文件出错:", e);
			}
		}
	}

	public static void delete(String filePath) {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				delete(f.getAbsolutePath());
			}
			file.delete();
		}
	}

	public static String getRelativePath(String baseDir, String absolutePath) {
		String relativePath = "";
		File dir = new File(baseDir);
		File file = new File(absolutePath);
		List<String> filenameList = new ArrayList<String>();
		filenameList.add(file.getName());
		while (file.getParentFile() != null) {
			if (dir.getAbsolutePath().equalsIgnoreCase(
					file.getParentFile().getAbsolutePath())) {
				break;
			} else {
				filenameList.add(file.getParentFile().getName());
			}
			file = file.getParentFile();
		}
		Collections.reverse(filenameList);
		for (String filename : filenameList) {
			relativePath = relativePath + File.separator + filename;
		}
		return relativePath;
	}

	public static String getParentPath(String path) {
		File file = new File(path);
		if (file.exists()) {
			return file.getParentFile().getAbsolutePath();
		} else {
			return "";
		}
	}

	public static boolean checkFilePostfix(File file, String... postfixExps) {
		String name = file.getName();
		String postfix = name.indexOf(".") >= 0 ? name.substring(
				name.indexOf("."), name.length()) : name;
		for (String postfixExp : postfixExps) {
			postfixExp = postfixExp.indexOf(".") >= 0 ? postfixExp.substring(
					postfixExp.indexOf("."), postfixExp.length()) : postfixExp;
			if (postfix.equalsIgnoreCase(postfixExp)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isReplaceFileType(File file, String[] replaceFileTypes) {
		boolean flag = false;
		String name = file.getName();
		String inputFileType = name.indexOf(".") >= 0 ? name.substring(
				name.indexOf("."), name.length()) : name;
		for (String fileType : replaceFileTypes) {
			fileType = fileType.indexOf(".") >= 0 ? fileType.substring(
					fileType.indexOf("."), fileType.length()) : fileType;
			if (inputFileType.equalsIgnoreCase(fileType)) {
				flag = true;
			}
		}
		return flag;

	}

	public static Map<String, TMStringKeyProperty> scanDir(
			Map<String, TMStringKeyProperty> totalMap, String projectPath,
			String fileType, TMCheckTypeEnum typeEnum) {
		File[] file = (new File(projectPath)).listFiles();
		for (int i = 0; i < file.length; i++) {
			String fileName = file[i].getName();
			switch (typeEnum) {
			case XML_DUPLICATE_DEFINITION:
				if (file[i].isDirectory()) {
					scanDir(totalMap, file[i].getPath(), fileType, typeEnum);
				} else {
					if (EXECLUSION_XML_FILES[0].equalsIgnoreCase(fileName)
							|| EXECLUSION_XML_FILES[1]
									.equalsIgnoreCase(fileName)) {
						continue;
					} else {
						if (fileName.endsWith(fileType)) {
							System.out.println(file[i].getPath());
							Map<String, TMStringKeyProperty> map = getProperty(file[i]
									.getPath());
							if (map != null && map.size() > 0) {
								totalMap.putAll(map);
							}
						}
					}
				}
				break;
			case RES_FILE_NAME_DUPLICATION:
				if (file[i].isDirectory()) {
					scanDir(totalMap, file[i].getPath(), fileType, typeEnum);
				} else {
					if (EXECLUSION_XML_FILES[0].equalsIgnoreCase(fileName)
							|| EXECLUSION_XML_FILES[1]
									.equalsIgnoreCase(fileName)) {
						continue;
					} else {
						// if (fileName.endsWith(fileType)) {
						System.out.println(file[i].getPath());
						TMStringKeyProperty p = new TMStringKeyProperty();
						p.setFullPath(file[i].getPath());
						totalMap.put(fileName, p);
						// }
					}
				}
				break;
			default:
				break;
			}

		}
		return totalMap;
	}

	private static Map<String, TMStringKeyProperty> getProperty(String xmlfile) {
		Map<String, TMStringKeyProperty> map = Collections.emptyMap();
		try {
			map = TMDom4JUtil.parserXml(xmlfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, TMResultProperty> checkCode(
			Map<String, TMStringKeyProperty> baseMap,
			Map<String, TMStringKeyProperty> newMap) {
		if (baseMap == null || newMap == null) {
			return null;
		}
		Map<String, TMResultProperty> resultMap = new HashMap<String, TMResultProperty>();
		for (Entry<String, TMStringKeyProperty> entry : newMap.entrySet()) {
			if (baseMap.get(entry.getKey()) != null) {
				TMResultProperty resultProperty = new TMResultProperty(
						baseMap.get(entry.getKey()), entry.getValue());
				resultMap.put(entry.getKey(), resultProperty);
			}
		}
		return resultMap;
	}

	/**
	 * 文件夹全路径，默认路径是newProjectPath 生成的文件夹名：TMCheckCodeResult_时间戳.txt
	 * 
	 * @param path
	 */
	public static String writeResultToFile(
			Map<String, TMResultProperty> resultMap, String path) {
		long timeStamp = System.currentTimeMillis();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(path + RESULT_FILE_NAME
					+ timeStamp + ".txt"), true);
			if (resultMap != null && resultMap.size() > 0) {
				String r = "-------total duplicate definition="
						+ resultMap.size() + "-------" + "\n";
				out.write(r.getBytes());
				for (Entry<String, TMResultProperty> entry : resultMap
						.entrySet()) {
					r = getOutString(entry);
					System.out.print(r);
					if (r != null) {
						out.write(r.getBytes());
					}
				}
			} else {
				String r = "No duplicate definition!";
				if (r != null) {
					out.write(r.getBytes());
				}
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return path;
	}

	private static String getOutString(Entry<String, TMResultProperty> entry) {
		StringBuilder b = new StringBuilder(entry.getKey());
		b.append("\n").append("    your project filePath=")
				.append(entry.getValue().getNewProperty().getFullPath())
				.append("\n");
		b.append("    main project filePath=").append(
				entry.getValue().getBaseProperty().getFullPath());
		b.append("\n");
		return b.toString();
	}

	public static void main(String[] args) throws IOException {
		/** -----name名称命名重复检测start------ **/
		// Map<String, TMStringKeyProperty> totalBaseMap = new HashMap<String,
		// TMStringKeyProperty>();
		// totalBaseMap = scanDir(totalBaseMap,
		// "D:\\program\\tmallandroid\\res",
		// ".xml", TMCheckTypeEnum.XML_DUPLICATE_DEFINITION);
		// System.out.println(totalBaseMap.size());
		// Map<String, TMStringKeyProperty> totalNewMap = new HashMap<String,
		// TMStringKeyProperty>();
		// totalNewMap = scanDir(totalNewMap,
		// "D:\\program\\tmallandroid_miaopackage\\res", ".xml",
		// TMCheckTypeEnum.XML_DUPLICATE_DEFINITION);
		// System.out.println(totalNewMap.size());
		// Map<String, TMResultProperty> resultMap = checkCode(totalBaseMap,
		// totalNewMap);
		// writeResultToFile(resultMap, REUSLT_DEFAULT_PATH);
		/** -----name名称命名重复检测end------ **/
		/** -----资源文件名重复检测start------ **/
		Map<String, TMStringKeyProperty> totalFileBaseMap = new HashMap<String, TMStringKeyProperty>();
		totalFileBaseMap = scanDir(totalFileBaseMap,
				"D:\\program\\tmallandroid\\res", ".xml",
				TMCheckTypeEnum.RES_FILE_NAME_DUPLICATION);
		System.out.println(totalFileBaseMap.size());
		Map<String, TMStringKeyProperty> totalFileNewMap = new HashMap<String, TMStringKeyProperty>();
		totalFileNewMap = scanDir(totalFileNewMap,
				"D:\\program\\tmallandroid_miaopackage\\res", ".xml",
				TMCheckTypeEnum.RES_FILE_NAME_DUPLICATION);
		System.out.println(totalFileNewMap.size());
		Map<String, TMResultProperty> resultMap = checkCode(totalFileBaseMap,
				totalFileNewMap);
		writeResultToFile(resultMap, REUSLT_DEFAULT_PATH);
		/** -----资源文件名重复检测end------ **/
	}
}
