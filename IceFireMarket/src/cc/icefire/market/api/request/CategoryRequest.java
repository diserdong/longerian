package cc.icefire.market.api.request;

import java.util.Map;

import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.CategoryResponse;

public class CategoryRequest extends BaseApiRequest<CategoryResponse> {

	private static final String path = "api/categories";
	
	@Override
	public Map<String, String> getTextParams(BaseApiContext context) {
		return null;
	}

	@Override
	public String getRequestURL(BaseApiContext context) {
		return context.getServer() + path;
	}

	@Override
	public Class<CategoryResponse> getResponseClass() {
		return CategoryResponse.class;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		return makeCachePath("api", "categoris");
	}

	/**
	 * cache lasts for 6 hours
	 */
	@Override
	public long getCacheTime() {
		return 60 * 60 * 6;
	}

}
