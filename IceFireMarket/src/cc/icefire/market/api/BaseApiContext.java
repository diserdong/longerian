package cc.icefire.market.api;

import crow.api.ApiContext;

public interface BaseApiContext extends ApiContext {
	/**
	 * 获取服务器地址，如正式地址与测试地址不同，可只在一个地方修改。
	 */
	public String getServer();
	
	/**
	 *开发测试用 
	 * @return
	 */
	public String getMockServer();
	
	/**
	 * 返回 API请求的AppKey
	 * @return
	 */
	public String getAppKey();
}
