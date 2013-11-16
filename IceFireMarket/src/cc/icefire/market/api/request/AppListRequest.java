package cc.icefire.market.api.request;

import java.util.HashMap;
import java.util.Map;

import cc.icefire.market.api.BaseApiContext;
import cc.icefire.market.api.response.AppListResponse;
import cc.icefire.market.model.AppListType;
import cc.icefire.market.model.AppOrGame;

public class AppListRequest extends BaseApiRequest<AppListResponse> {

	private static final String path = "api/list";
	
	private AppListType type;
	private int pageIndex = 0;
	private int pageSize = 10;
	private AppOrGame appOrGame; //仅非搜索时使用，1 应用， 0 游戏
	private String query; //仅搜索时使用
	
	public AppListType getType() {
		return type;
	}

	public void setType(AppListType type) {
		this.type = type;
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
		if(type == AppListType.SEARCH) {
			businessParams.put("query", query);
		} else {
			businessParams.put("appOrGame", appOrGame + "");
		}
		businessParams.put("pageIndex", pageIndex + "");
		businessParams.put("pageSize", pageSize + "");
		return businessParams;
	}

	@Override
	public String getRequestURL(BaseApiContext context) {
		return context.getServer() + path;
	}

	@Override
	public Class<AppListResponse> getResponseClass() {
		return AppListResponse.class;
	}

	@Override
	public String getCacheRelativePathOrURL() {
		return makeCachePath("api", "applist", type.toString(), query, appOrGame == AppOrGame.APP ? "app" : "game", pageIndex + "", pageSize + "");
	}

	@Override
	public long getCacheTime() {
		return 10 * 60;
	}

}
