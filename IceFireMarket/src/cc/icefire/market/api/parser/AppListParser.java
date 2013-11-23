package cc.icefire.market.api.parser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cc.icefire.market.BuildConfig;
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
		if(BuildConfig.DEBUG) {
			List<BasicAppItem> apps = new ArrayList<BasicAppItem>();
			BasicAppItem b1 = new BasicAppItem("0", "ZenDay", "com.zenday-app", 0, "1.2", null, 0, "http://iconurl", 4, 50000, "http://downloadurl", "", (int) (4.7 * 1024 * 1024), null);
			BasicAppItem b2 = new BasicAppItem("0", "ZenDay", "com.zenday-app", 0, "1.2", null, 0, "http://iconurl", 4, 50000, "http://downloadurl", "", (int) (4.7 * 1024 * 1024), null);
			BasicAppItem b3 = new BasicAppItem("0", "ZenDay", "com.zenday-app", 0, "1.2", null, 0, "http://iconurl", 4, 50000, "http://downloadurl", "", (int) (4.7 * 1024 * 1024), null);
			BasicAppItem b4 = new BasicAppItem("0", "ZenDay", "com.zenday-app", 0, "1.2", null, 0, "http://iconurl", 4, 50000, "http://downloadurl", "", (int) (4.7 * 1024 * 1024), null);
			BasicAppItem b5 = new BasicAppItem("0", "ZenDay", "com.zenday-app", 0, "1.2", null, 0, "http://iconurl", 4, 50000, "http://downloadurl", "", (int) (4.7 * 1024 * 1024), null);
			BasicAppItem b6 = new BasicAppItem("0", "ZenDay", "com.zenday-app", 0, "1.2", null, 0, "http://iconurl", 4, 50000, "http://downloadurl", "", (int) (4.7 * 1024 * 1024), null);
			apps.add(b1);
			apps.add(b2);
			apps.add(b3);
			apps.add(b4);
			apps.add(b5);
			apps.add(b6);
			appList.setAppList(apps);
			appList.setSuccess(true);
		} else {
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

}
