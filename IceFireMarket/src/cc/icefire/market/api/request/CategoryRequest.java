package cc.icefire.market.api.request;

import java.util.HashMap;
import java.util.Map;

import cc.icefire.market.BuildConfig;
import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.CategoryResponse;
import cc.icefire.market.model.AppOrGame;

public class CategoryRequest extends BaseApiRequest<CategoryResponse> {

	private static final String path = "api/categories";
	
	private final AppOrGame type;
	
	public CategoryRequest(AppOrGame type) {
		this.type = type;
	}
	
	public AppOrGame getType() {
		return type;
	}

	@Override
	public Map<String, String> getTextParams(BaseApiContext context) {
		HashMap<String, String> businessParams = new HashMap<String, String>();
		businessParams.put("type", type.ordinal() + "");
		return businessParams;
	}

	@Override
	public String getRequestURL(BaseApiContext context) {
		if(BuildConfig.DEBUG) {
			return context.getMockServer();
		} else {
			return context.getServer() + path;
		}
	}

	@Override
	public Class<CategoryResponse> getResponseClass() {
		return CategoryResponse.class;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		return makeCachePath("api", "categoris", type == AppOrGame.APP ? "app" : "game");
	}

	/**
	 * cache lasts for 6 hours
	 */
	@Override
	public long getCacheTime() {
		return 60 * 60 * 6;
	}

}
