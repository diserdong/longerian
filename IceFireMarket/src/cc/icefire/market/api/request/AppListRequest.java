package cc.icefire.market.api.request;

import java.util.HashMap;
import java.util.Map;

import cc.icefire.market.BuildConfig;
import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.AppListResponse;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;

public class AppListRequest extends BaseApiRequest<AppListResponse> {

	private static final String path = "api/list";
	
	private final AppListType type;
	private int pageIndex = 0;
	private int pageSize = 10;
	private AppOrGame appOrGame; //0 游戏， 1 应用，2.任意 
	private String query; //仅搜索时使用
	private int categoryId; //仅分类目录下应用时使用
	
	public AppListRequest(AppListType type) {
		this.type = type;
	}
	
	public AppListType getType() {
		return type;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	public AppOrGame getAppOrGame() {
		return appOrGame;
	}

	public void setAppOrGame(AppOrGame appOrGame) {
		this.appOrGame = appOrGame;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public Map<String, String> getTextParams(BaseApiContext context) {
		HashMap<String, String> businessParams = new HashMap<String, String>();
		businessParams.put("type", type.ordinal() + "");
		switch (type) {
		case SEARCH:
			businessParams.put("query", query);
			break;
		case CATEGORY:
			businessParams.put("categoryId", categoryId + "");
			break;
		default:
			break;
		}
		businessParams.put("appOrGame", appOrGame + "");
		businessParams.put("pageIndex", pageIndex + "");
		businessParams.put("pageSize", pageSize + "");
		return businessParams;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Class<AppListResponse> getResponseClass() {
		return AppListResponse.class;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		switch (type) {
		case SEARCH:
			return makeCachePath("api", "applist", type.toString(), query, pageIndex + "", pageSize + "");
		case CATEGORY:
			return makeCachePath("api", "applist", type.toString(), categoryId + "", appOrGame == AppOrGame.APP ? "app" : "game", pageIndex + "", pageSize + "");
		default:
			return makeCachePath("api", "applist", type.toString(), appOrGame == AppOrGame.APP ? "app" : "game", pageIndex + "", pageSize + "");
		}
	}

	@Override
	public long getCacheTime() {
		return 10 * 60;
	}

}
