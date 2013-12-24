package cc.icefire.market.api.request;

import java.util.Map;

import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.TrendResponse;

/**
 * 搜索关键词请求接口，
 * 方法：POST 
 * 路径：/api/trends
 * 参数： 无
 */
public class TrendRequest extends BaseApiRequest<TrendResponse> {

	private static final String path = "api/trends";
	
	@Override
	public Map<String, String> getTextParams(BaseApiContext context) {
		return null;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Class<TrendResponse> getResponseClass() {
		return TrendResponse.class;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		return makeCachePath("api", "trends");
	}

	/**
	 * cache lasts for 6 hours
	 */
	@Override
	public long getCacheTime() {
		return 60 * 60 * 6;
	}

}
