/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.praser.performance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类FileUtil.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-10-22 下午5:24:30
 */
public class FileUtil {

    public static Map<String, List<DataDO>> readFile(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        Map<String, List<DataDO>> dataMap = new HashMap<String, List<DataDO>>();
        List<DataDO> lanuchList = new ArrayList<DataDO>();
        List<DataDO> homeList = new ArrayList<DataDO>();
        List<DataDO> searchList = new ArrayList<DataDO>();
        List<DataDO> detailList = new ArrayList<DataDO>();
        List<DataDO> imageDescList = new ArrayList<DataDO>();
        List<DataDO> orderConfirmList = new ArrayList<DataDO>();
        List<DataDO> orderCreateList = new ArrayList<DataDO>();
        List<DataDO> shopList = new ArrayList<DataDO>();
        List<DataDO> cartList = new ArrayList<DataDO>();
        List<DataDO> dynativeList = new ArrayList<DataDO>();

        // 一次读取一行
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_LAUNCH)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_LAUNCH.equals(d.getPageName())) {
                        lanuchList.add(d);
                    }
                } else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_HOME)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_HOME.equals(d.getPageName())) {
                        homeList.add(d);
                    }
                } else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_ITEM_LISTVIEW)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_ITEM_LISTVIEW.equals(d.getPageName())) {
                        searchList.add(d);
                    }
                } else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_DETAIL_IMAGE_DESC)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_DETAIL_IMAGE_DESC.equals(d.getPageName())) {
                        imageDescList.add(d);
                    }
                } else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_DETAIL)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_DETAIL.equals(d.getPageName())) {
                        detailList.add(d);
                    }
                } else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_ORDER_CONFIRM)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_ORDER_CONFIRM.equals(d.getPageName())) {
                        orderConfirmList.add(d);
                    }
                } else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_CREATE_ORDER)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_CREATE_ORDER.equals(d.getPageName())) {
                        orderCreateList.add(d);
                    }
                } else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_SHOP)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_SHOP.equals(d.getPageName())) {
                        shopList.add(d);
                    }
                } else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_CART)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && ITMPerformanceConstants.PAGE_NAME_PAGE_CART.equals(d.getPageName())) {
                        cartList.add(d);
                    }
                }  else if (tempString.contains(ITMPerformanceConstants.PAGE_NAME_PAGE_DYNATIVE)) {
                    DataDO d = getStringToDO(tempString);
                    if (d != null && d.getPageName().contains(ITMPerformanceConstants.PAGE_NAME_PAGE_DYNATIVE)) {
                        dynativeList.add(d);
                    }
                }
                // for (String pageName : pageNames) {
                // if (tempString.contains(pageName)) {
                // DataDO d = getStringToDO(tempString);
                // dataList.add(d);
                // }
                // }
            }
            reader.close();
            if (lanuchList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_LAUNCH, lanuchList);
            }
            if (homeList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_HOME, homeList);
            }
            if (searchList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_ITEM_LISTVIEW, searchList);
            }
            if (detailList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_DETAIL, detailList);
            }
            if (imageDescList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_DETAIL_IMAGE_DESC, imageDescList);
            }
            if (orderConfirmList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_ORDER_CONFIRM, orderConfirmList);
            }
            if (orderCreateList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_CREATE_ORDER, orderCreateList);
            }
            if (shopList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_SHOP, shopList);
            }
            if (cartList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_CART, cartList);
            }
            if (dynativeList.size() > 0) {
                dataMap.put(ITMPerformanceConstants.PAGE_NAME_PAGE_DYNATIVE, dynativeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataMap;
    }

    public static DataDO getStringToDO(String s) {
        DataDO d = new DataDO();
        if (s != null && s.contains("TMPerformanceTrack:")) {
            String[] tm = s.split("TMPerformanceTrack:");
            if (tm != null && tm.length >= 2) {
                String s1 = tm[1];
                if (s1 != null) {
                    String[] ar = s1.split(",");
                    if (ar != null && ar.length > 0) {
                        for (String str : ar) {
                            String[] a = str.split("=");
                            if (a != null && a.length == 2) {
                                if ("pageName".equals(a[0].trim())) {
                                    d.setPageName(a[1]);
                                } else if ("MethodName".equals(a[0].trim())) {
                                    d.setMethodName(a[1]);
                                } else if ("DetailName".equals(a[0].trim())) {
                                    d.setDetailName(a[1]);
                                } else if ("CostTime".equals(a[0].trim())) {
                                    if (a[1] != null && a[1].length() > 2) {
                                        String t = a[1].substring(0, a[1].length() - 2);
                                        if (t != null) {
                                            d.setCostTime(Long.valueOf(t));
                                        }

                                    }

                                }
                            }
                        }

                    }
                }
            }

        }
        return d;
    }

    public static String writeFile(String fileContent, String fileName) {
        // Long date = System.currentTimeMillis();
        String path = null;
        // String resultFileName = fileName + "-merge-" + date;
        FileOutputStream out = null;
        try {
            // File f = new File(fileName);
            // OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            // BufferedWriter writer = new BufferedWriter(write);
            // writer.write(fileContent);
            // write.close();
            out = new FileOutputStream(new File(fileName), true);
            out.write(fileContent.getBytes());
            out.close();
            path = fileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return path;

    }
}
