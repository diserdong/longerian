package cc.icefire.market.api.request;

import java.util.Map;

import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.TestResponse;
import crow.cache.Cache;

public class TestRequest extends BaseApiRequest<TestResponse> {

	@Override
	public String getRequestURL(BaseApiContext ctx) {
		return ctx.getServer();
	}

	@Override
	public Class<TestResponse> getResponseClass() {
		return TestResponse.class;
	}

	@Override
	public Map<String, String> getTextParams(BaseApiContext ctx) {
		return null;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		return makeCachePath("api", "test");
	}

	@Override
	public long getCacheTime() {
		return Cache.EXPIRED;
	}


}
