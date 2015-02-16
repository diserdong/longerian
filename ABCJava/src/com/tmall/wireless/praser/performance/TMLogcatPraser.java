/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.praser.performance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 类TMLogcatPraser.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-10-22 下午5:19:29
 */
public class TMLogcatPraser {

    public static String[] PAGE_NAMES   = { "Page_Home", "Page_Shop", "Page_ItemListView", "Page_Detail",
            "Page_DetailImageDesc", "Page_CreateOrder", "Page_OrderConfirm", "Page_Launch", "Page_Pay", "Page_Cart" };
    public static String[] METHOD_NAMES = { "Page_Home", "Page_Shop", "Page_ItemListView", "Page_Detail",
            "Page_DetailImageDesc", "Page_CreateOrder", "Page_OrderConfirm", "Page_Launch", "Page_Pay", "Page_Cart" };

    public static void main(String[] args) {
        Map<String, List<DataDO>> map = FileUtil.readFile("D:\\dynative.txt");
        if (map != null && map.size() > 0) {
            for (Entry<String, List<DataDO>> entry : map.entrySet()) {
                if (ITMPerformanceConstants.PAGE_NAME_PAGE_LAUNCH.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_LAUNCH-----------------------");
                    getCostTime(entry.getValue(), true);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_HOME.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_HOME-----------------------");
                    getCostTime(entry.getValue(), false);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_ITEM_LISTVIEW.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_ITEM_LISTVIEW-----------------------");
                    getCostTime(entry.getValue(), false);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_DETAIL.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_DETAIL-----------------------");
                    getCostTime(entry.getValue(), false);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_DETAIL_IMAGE_DESC.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_DETAIL_IMAGE_DESC-----------------------");
                    getCostTime(entry.getValue(), false);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_ORDER_CONFIRM.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_ORDER_CONFIRM-----------------------");
                    getCostTime(entry.getValue(), false);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_CREATE_ORDER.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_CREATE_ORDER-----------------------");
                    getCostTime(entry.getValue(), false);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_SHOP.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_SHOP-----------------------");
                    getCostTime(entry.getValue(), false);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_CART.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_CART-----------------------");
                    getCostTime(entry.getValue(), false);
                } else if (ITMPerformanceConstants.PAGE_NAME_PAGE_DYNATIVE.equals(entry.getKey())) {
                    System.out.println("--------------PAGE_NAME_PAGE_DYNATIVE-----------------------");
                    getCostTime(entry.getValue(), false);
                }
            }
        }

    }

    public static Map<String, Long> getCostTime(List<DataDO> dList, boolean isLaunch) {
        // if (ITMPerformanceConstants.METHOD_NAME_LAUNCH_TIME.equals(type)) {

        Map<String, Long> map = new HashMap<String, Long>();
        if (isLaunch) {
            long launchTime = 0l;
            long launchCount = 0l;
            long launchTime0 = 0l;
            long launchCount0 = 0l;
            long launchTime1 = 0l;
            long launchCount1 = 0l;
            long launchTime2 = 0l;
            long launchCount2 = 0l;
            for (DataDO d : dList) {
                if (d.getCostTime() != null && d.getCostTime() > 0) {
                    if (isLaunch) {
                        if (ITMPerformanceConstants.METHOD_NAME_LAUNCH_TIME.equals(d.getMethodName())) {
                            launchTime += d.getCostTime();
                            launchCount++;
                            if ("0".equals(d.getDetailName())) {
                                launchTime0 += d.getCostTime();
                                launchCount0++;
                            } else if ("1".equals(d.getDetailName())) {
                                launchTime1 += d.getCostTime();
                                launchCount1++;
                            } else if ("2".equals(d.getDetailName())) {
                                launchTime2 += d.getCostTime();
                                launchCount2++;
                            }
                        }
                    }
                }
            }
            if (launchTime > 0 && launchCount > 0) {
                launchTime = launchTime / launchCount;
                System.out.println("launchTime=" + launchTime);
                if (launchTime > 0) {
                    map.put(ITMPerformanceConstants.METHOD_NAME_LAUNCH_TIME, launchTime);
                }
            }
            if (launchTime0 > 0 && launchCount0 > 0) {
                launchTime0 = launchTime0 / launchCount0;
                System.out.println("launchTime0=" + launchTime0);
            }
            if (launchTime1 > 0 && launchCount1 > 0) {
                launchTime1 = launchTime1 / launchCount1;
                System.out.println("launchTime1=" + launchTime1);
            }
            if (launchTime2 > 0 && launchCount2 > 0) {
                launchTime2 = launchTime2 / launchCount2;
                System.out.println("launchTime2=" + launchTime2);
            }
        } else {
            long loadTime = 0l;
            long loadCount = 0l;
            long requestTime = 0l;
            long requestCount = 0l;
            long reloadTime = 0l;
            long reloadCount = 0l;
            long totalTime = 0l;
            long totalCount = 0l;
            long loadTime0 = 0l;
            long loadCount0 = 0l;
            long requestTime0 = 0l;
            long requestCount0 = 0l;
            long reloadTime0 = 0l;
            long reloadCount0 = 0l;
            long totalTime0 = 0l;
            long totalCount0 = 0l;
            long loadTime1 = 0l;
            long loadCount1 = 0l;
            long requestTime1 = 0l;
            long requestCount1 = 0l;
            long reloadTime1 = 0l;
            long reloadCount1 = 0l;
            long totalTime1 = 0l;
            long totalCount1 = 0l;
            long loadTime2 = 0l;
            long loadCount2 = 0l;
            long requestTime2 = 0l;
            long requestCount2 = 0l;
            long reloadTime2 = 0l;
            long reloadCount2 = 0l;
            long totalTime2 = 0l;
            long totalCount2 = 0l;
            long loadTime3 = 0l;
            long loadCount3 = 0l;
            long requestTime3 = 0l;
            long requestCount3 = 0l;
            long reloadTime3 = 0l;
            long reloadCount3 = 0l;
            long totalTime3 = 0l;
            long totalCount3 = 0l;
            for (DataDO d : dList) {
                if (d.getCostTime() != null && d.getCostTime() > 0) {
                    if (ITMPerformanceConstants.METHOD_NAME_LOAD_TIME.equals(d.getMethodName())) {
                        loadTime += d.getCostTime();
                        loadCount++;
                        if ("0".equals(d.getDetailName())) {
                            loadTime0 += d.getCostTime();
                            loadCount0++;
                        } else if ("1".equals(d.getDetailName())) {
                            loadTime1 += d.getCostTime();
                            loadCount1++;
                        } else if ("2".equals(d.getDetailName())) {
                            loadTime2 += d.getCostTime();
                            loadCount2++;
                        } else if ("3".equals(d.getDetailName())) {
                            loadTime3 += d.getCostTime();
                            loadCount3++;
                        }
                    } else if (ITMPerformanceConstants.METHOD_NAME_REQUEST_TIME.equals(d.getMethodName())) {
                        requestTime += d.getCostTime();
                        requestCount++;
                        if ("0".equals(d.getDetailName())) {
                            requestTime0 += d.getCostTime();
                            requestCount0++;
                        } else if ("1".equals(d.getDetailName())) {
                            requestTime1 += d.getCostTime();
                            requestCount1++;
                        } else if ("2".equals(d.getDetailName())) {
                            requestTime2 += d.getCostTime();
                            requestCount2++;
                        } else if ("3".equals(d.getDetailName())) {
                            requestTime3 += d.getCostTime();
                            requestCount3++;
                        }
                    } else if (ITMPerformanceConstants.METHOD_NAME_RELOAD_TIME.equals(d.getMethodName())) {
                        reloadTime += d.getCostTime();
                        requestCount++;
                        if ("0".equals(d.getDetailName())) {
                            reloadTime0 += d.getCostTime();
                            reloadCount0++;
                        } else if ("1".equals(d.getDetailName())) {
                            reloadTime1 += d.getCostTime();
                            reloadCount1++;
                        } else if ("2".equals(d.getDetailName())) {
                            reloadTime2 += d.getCostTime();
                            reloadCount2++;
                        } else if ("3".equals(d.getDetailName())) {
                            reloadTime3 += d.getCostTime();
                            reloadCount3++;
                        }
                    } else if (ITMPerformanceConstants.METHOD_NAME_LOAD_TOTAL.equals(d.getMethodName())) {
                        totalTime += d.getCostTime();
                        totalCount++;
                        if ("0".equals(d.getDetailName())) {
                            reloadTime0 += d.getCostTime();
                            reloadCount0++;
                        } else if ("1".equals(d.getDetailName())) {
                            reloadTime1 += d.getCostTime();
                            reloadCount1++;
                        } else if ("2".equals(d.getDetailName())) {
                            reloadTime2 += d.getCostTime();
                            reloadCount2++;
                        } else if ("3".equals(d.getDetailName())) {
                            reloadTime3 += d.getCostTime();
                            reloadCount3++;
                        }
                    }

                }
            }
            if (loadTime > 0 && loadCount > 0) {
                loadTime = loadTime / loadCount;
                System.out.println("loadTime=" + loadTime);
                if (loadTime > 0) {
                    map.put(ITMPerformanceConstants.METHOD_NAME_LOAD_TIME, loadTime);
                }
            }
            if (requestTime > 0 && requestCount > 0) {
                requestTime = requestTime / requestCount;
                System.out.println("requestTime=" + requestTime);
                if (requestTime > 0) {
                    map.put(ITMPerformanceConstants.METHOD_NAME_REQUEST_TIME, requestTime);
                }
            }
            if (reloadTime > 0 && reloadCount > 0) {
                reloadTime = reloadTime / reloadCount;
                System.out.println("reloadTime=" + reloadTime);
                if (reloadTime > 0) {
                    map.put(ITMPerformanceConstants.METHOD_NAME_RELOAD_TIME, reloadTime);
                }
            }
            if (totalTime > 0 && totalCount > 0) {
                totalTime = totalTime / totalCount;
                System.out.println("totalTime=" + totalTime);
                if (totalTime > 0) {
                    map.put(ITMPerformanceConstants.METHOD_NAME_LOAD_TOTAL, totalTime);
                }
            }
            printDetail(loadTime0, loadCount0, "loadTime0");
            printDetail(requestTime0, requestCount0, "requestTime0");
            printDetail(reloadTime0, reloadCount0, "reloadTime0");
            printDetail(totalTime0, totalCount0, "totalTime0");
            printDetail(loadTime1, loadCount1, "loadTime1");
            printDetail(requestTime1, requestCount1, "requestTime1");
            printDetail(reloadTime1, reloadCount1, "reloadTime1");
            printDetail(totalTime1, totalCount1, "totalTime1");
            printDetail(loadTime2, loadCount2, "loadTime2");
            printDetail(requestTime2, requestCount2, "requestTime2");
            printDetail(reloadTime2, reloadCount2, "reloadTime2");
            printDetail(totalTime2, totalCount2, "totalTime2");
            printDetail(loadTime3, loadCount3, "loadTime3");
            printDetail(requestTime3, requestCount3, "requestTime3");
            printDetail(reloadTime3, reloadCount3, "reloadTime3");
            printDetail(totalTime3, totalCount3, "totalTime3");
        }
        return map;
    }

    private static void printDetail(long time, long count, String type) {
        if (time > 0 && count > 0) {
            time = time / count;
            System.out.println(type + "=" + time);
        }

    }
}
