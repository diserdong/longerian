/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.tmall.wireless.praser.performance;

/**
 * 类LaunchEnum.java的实现描述：TODO 类实现描述
 * 
 * @author zhiyong.lizy 2014-10-22 下午5:52:14
 */
public enum LaunchEnum {

    FIRST_LAUNCH(2, "firstLaunch"), COOL_LAUNCH(1, "coolLaunch"), HOT_LAUNCH(0, "hotLaunch");

    private String value;
    private int    i;

    private LaunchEnum(int i, String v){
        this.value = v;
        this.i = i;
    }
}
