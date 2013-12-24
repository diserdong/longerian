package cc.icefire.market.api.request;

import java.util.HashMap;
import java.util.Map;

import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.CommentResponse;

/**
 * 评论列表请求接口，
 * 方法：POST 
 * 路径：/api/comments
 * 参数： appId=xxx&pkgName=xxxx&pageIndex=1&pageSize=10
 */
public class CommentRequest extends BaseApiRequest<CommentResponse> {

	private static final String path = "api/comments";
	
	/**
	 * 应用id
	 */
	private final String appId;
	/**
	 * 应用包名
	 */
	private final String pkgName;
	/**
	 * 评论页标，从1开始计算
	 */
	private int pageIndex = 1;
	/**
	 * 每页大小
	 */
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
	public String getPath() {
		return path;
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
