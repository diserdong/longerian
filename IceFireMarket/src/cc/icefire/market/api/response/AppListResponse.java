package cc.icefire.market.api.response;

import java.util.List;

import cc.icefire.market.model.BasicAppItem;

/**
 * 统一应用列表响应，可能是分类目录下的应用列表，可能是搜索结果的应用列表，也可能是各种排行的应用列表
 */
public class AppListResponse extends BaseApiResponse {

	private List<BasicAppItem> appList;

	public List<BasicAppItem> getAppList() {
		return appList;
	}

	public void setAppList(List<BasicAppItem> appList) {
		this.appList = appList;
	}
	
}
