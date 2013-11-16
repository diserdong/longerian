package cc.icefire.market.api.response;

import java.util.List;

import cc.icefire.market.model.Trend;

/**
 * 热门搜索关键词响应
 */
public class TrendResponse extends BaseApiResponse {

	private List<Trend> trends;

	public List<Trend> getTrends() {
		return trends;
	}

	public void setTrends(List<Trend> trends) {
		this.trends = trends;
	}
	
}
