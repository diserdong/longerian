/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.praser.performance;

/**
 * 类DataDO.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-10-22 下午5:41:56
 */
public class DataDO {

    private String pageName;
    private String methodName;
    private String detailName;
    private Long   costTime;

    /**
     * @return the pageName
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * @param pageName the pageName to set
     */
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * @return the detailName
     */
    public String getDetailName() {
        return detailName;
    }

    /**
     * @param detailName the detailName to set
     */
    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    /**
     * @return the costTime
     */
    public Long getCostTime() {
        return costTime;
    }

    /**
     * @param costTime the costTime to set
     */
    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

}
