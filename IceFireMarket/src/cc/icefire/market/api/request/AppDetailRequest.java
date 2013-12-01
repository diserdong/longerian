package cc.icefire.market.api.request;

import java.util.HashMap;
import java.util.Map;

import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.AppDetailResponse;

public class AppDetailRequest extends BaseApiRequest<AppDetailResponse> {

	private static final String path = "api/detail";
	
	private final String appId;
	private final String pkgName;
	
	public AppDetailRequest(String appId, String pkgName) {
		this.appId = appId;
		this.pkgName = pkgName;
	}
	
	public String getAppId() {
		return appId;
	}

	public String getPkgName() {
		return pkgName;
	}

	@Override
	public Map<String, String> getTextParams(BaseApiContext context) {
		HashMap<String, String> businessParams = new HashMap<String, String>();
		businessParams.put("appId", appId);
		businessParams.put("pkgName", pkgName);
		return businessParams;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Class<AppDetailResponse> getResponseClass() {
		return AppDetailResponse.class;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		return makeCachePath("api", "appdetail", appId);
	}

	@Override
	public long getCacheTime() {
		return 10 * 60;
	}

}
