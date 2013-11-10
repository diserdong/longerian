package cc.icefire.market.api.request;

import java.util.HashMap;
import java.util.Map;

import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.TestResponse;
import crow.cache.Cache;

public class TestRequest extends BaseApiRequest<TestResponse> {

	@Override
	public String getMethod() {
		return "getproductinfo";
	}

	@Override
	public String getRequestURL(BaseApiContext ctx) {
		return ctx.getServer() + "getproductinfo.ashx";
	}

	@Override
	public Class<TestResponse> getResponseClass() {
		return TestResponse.class;
	}

	@Override
	public Map<String, String> getTextParams(BaseApiContext ctx) {
		Map<String, String> map = new HashMap<String, String>();		
		map.put("prodeuctcode", "JW10120110");
		return map;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		return "test";
	}

	@Override
	public long getCacheTime() {
		return Cache.EXPIRED;
	}

	@Override
	public Class<?> getHttpRequestClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
