package com.tmall.wireless.praser.performance;

/*
 * Copyright 2014 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
/**
 * @author zhiyong.lizy 2014-10-1 下午3:08:33
 */
public interface ITMPerformanceConstants {

	/** 性能埋点ID对应的Event ID */
	public static final int PAGE_EVID_DEFAULT = 21034;
	public static final int PAGE_EVID_HOME = 21028;
	public static final int PAGE_EVID_SHOP = 65184;//65184
	public static final int PAGE_EVID_OLD_SHOP = 65184;//65184
	public static final int PAGE_EVID_SEARCH = 65171;
	public static final int PAGE_EVID_DETAIL = 65172;
	public static final int PAGE_EVID_DETAIL_DESC = 65185;//65185
	public static final int PAGE_EVID_CREARE_ORDER = 65183;//下单   65183 下单
	public static final int PAGE_EVID_CONFIRM_ORDER = 65174;//下单页
	public static final int PAGE_EVID_DYNATIVE = 65177;
	public static final int PAGE_EVID_CART=65178;
	public static final int PAGE_EVID_PAY=65176;
	public static final int PAGE_EVID_FUN=65179;
	

	// Track Event IDs
	public static final int TK_EVID_APP_LAUNCH = 21028;
	public static final int TK_EVID_NETWORK = 26690;
	public static final int TK_EVID_IMG_DOWNLOAD = 65131;

	/** 性能埋点ID对应的页面名称 */
	public final static String PAGE_NAME_PAGE_HOME = "Page_Home";
	public  final static String PAGE_NAME_PAGE_SHOP = "Page_Shop";
	public final static String PAGE_NAME_PAGE_OLD_SHOP = "Page_OldShop";
	public final static String PAGE_NAME_PAGE_ITEM_LISTVIEW = "Page_ItemListView";//搜索的
	public final static String PAGE_NAME_PAGE_DETAIL = "Page_Detail";
	public final static String PAGE_NAME_PAGE_DETAIL_IMAGE_DESC = "Page_DetailImageDesc";
	public static String PAGE_NAME_PAGE_CREATE_ORDER = "Page_CreateOrder";
	public final static String PAGE_NAME_PAGE_ORDER_CONFIRM = "Page_OrderConfirm";
	public final static String PAGE_NAME_PAGE_LAUNCH = "Page_Launch";
	public final static String PAGE_NAME_PAGE_NETWORK_DOWNLOADER = "NetworkDownloader";
	public final static String PAGE_NAME_PAGE_IMAGE_DWONLOADER = "ImageDownloader";
	public final static String PAGE_NAME_PAGE_DYNATIVE="Page_Dynative_tmall";
	public final static String PAGE_NAME_PAGE_FUN="Page_Fun";
	public final static String PAGE_NAME_PAGE_PAY="Page_Pay";
	public final static String PAGE_NAME_PAGE_CART="Page_Cart";
	
	/**
	 * 性能埋点对应的方法名称
	 */
    public final static String METHOD_NAME_LOAD_TIME="LoadTime";
    public final static String METHOD_NAME_REQUEST_TIME="RequestTime";
    public final static String METHOD_NAME_PRASE_TIME="DataPraseTime";
    public final static String METHOD_NAME_RELOAD_TIME="RenderTime";
    public final static String METHOD_NAME_LOAD_TOTAL="load";
    public final static String METHOD_NAME_LAUNCH_TIME="Launch";
    
    public final static String DETAIL_NAME_SEARCH_HOME="0";
    public final static String DETAIL_NAME_SEARCH_GLOABL_ITEM="1";//全局商品搜索
    public final static String DETAIL_NAME_SEARCH_GLOABL_SHOP="2";//全局店铺搜索
    public final static String DETAIL_NAME_SEARCH_SHOP_ITEM="3";//店铺内商品搜索

}
