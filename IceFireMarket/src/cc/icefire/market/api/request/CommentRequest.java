package cc.icefire.market.api.request;

import java.util.HashMap;
import java.util.Map;

import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.CommentResponse;

public class CommentRequest extends BaseApiRequest<CommentResponse> {

	private static final String path = "api/categories";
	
	private final String appId;
	private final String pkgName;
	private int pageIndex = 0;
	private int pageSize = 10;
	
	public CommentRequest(String appId, String pkgName) {
		this.appId = appId;
		this.pkgName = pkgName;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
		businessParams.put("pageIndex", pageIndex + "");
		businessParams.put("pageSize", pageSize + "");
		return businessParams;
	}

	@Override
	public String getRequestURL(BaseApiContext context) {
		return context.getServer() + path;
	}

	@Override
	public Class<CommentResponse> getResponseClass() {
		return CommentResponse.class;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		return makeCachePath("api", "comments", appId, pageIndex + "", pageSize + "");
	}

	@Override
	public long getCacheTime() {
		return 10 * 60;
	}

}
