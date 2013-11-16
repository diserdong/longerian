package cc.icefire.market.api.parser;

import java.lang.reflect.Type;
import java.util.List;

import cc.icefire.market.api.response.AppListResponse;
import cc.icefire.market.model.BasicAppItem;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class AppListParser extends JsonParseHandler<AppListResponse> {

	private AppListResponse appList = new AppListResponse();
	
	@Override
	public AppListResponse getModel() {
		return appList;
	}

	@Override
	public void parse(String inputSource) {
		try {
			Type type = new TypeToken<List<BasicAppItem>>(){}.getType();
			List<BasicAppItem> apps = JsonUtil.fromJsonArray(inputSource, type);
			appList.setAppList(apps);
			appList.setSuccess(true);
		} catch (JsonSyntaxException e) {
			appList.setSuccess(false);
			e.printStackTrace();
		} catch (JsonParseException e) {
			appList.setSuccess(false);
			e.printStackTrace();
		}
	}

}
