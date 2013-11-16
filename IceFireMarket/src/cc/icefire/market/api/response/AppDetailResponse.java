package cc.icefire.market.api.response;

import cc.icefire.market.model.BasicAppItem;

public class AppDetailResponse extends BaseApiResponse {

	private BasicAppItem appItem;

	public BasicAppItem getAppItem() {
		return appItem;
	}

	public void setAppItem(BasicAppItem appItem) {
		this.appItem = appItem;
	}
	
}
