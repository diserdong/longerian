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
		if (BuildConfig.DEBUG) {
			List<BasicAppItem> apps = new ArrayList<BasicAppItem>();
			String desp = "With ZenDay, be better organized, maintain your work/life balance and fight procrastination."
					+ "Our users say it best:"
					+ "ZenDay is a functional and beautiful way to organize your schedule."
					+ "It presents a unified view of Tasks and Appointments, by presenting them as a 3D Rolodex."
					+ "The seamless integration of Tasks / Events / Google calendar makes it the perfect tool to manage your day."
					+ "ZenDay is a task list that really works."
					+ "This is time management as it *should* be."
					+ "And it's already changing my life and work for the better.";
			String iconUrl = "http://img.wdjimg.com/mms/icon/v1/3/bd/5295ac6a9c6d51e8285690bdbe1b1bd3_256_256.png";
			String downloadUrl = "http://gdown.baidu.com/data/wisegame/f3591c4b8b7d4953/SolCalendar_58.apk";
			String[] screendshots = new String[] {iconUrl, iconUrl, iconUrl, iconUrl, iconUrl, iconUrl};
			BasicAppItem b1 = new BasicAppItem("0", "ZenDay", "com.wandoujia.phoenix2",
					0, "1.2", null, 0, iconUrl, 4, 50000,
					downloadUrl, desp, (int) (4.7 * 1024 * 1024), screendshots);
			BasicAppItem b2 = new BasicAppItem("0", "ZenDay", "com.wandoujia.phoenix2",
					200000, "1.2", null, 0, iconUrl, 4, 50000,
					downloadUrl, desp, (int) (4.7 * 1024 * 1024), screendshots);
			BasicAppItem b3 = new BasicAppItem("0", "ZenDay", "com.zenday-app",
					0, "1.2", null, 0, iconUrl, 4, 50000,
					downloadUrl, desp, (int) (4.7 * 1024 * 1024), screendshots);
			BasicAppItem b4 = new BasicAppItem("0", "ZenDay", "com.zenday-app",
					0, "1.2", null, 0, iconUrl, 4, 50000,
					downloadUrl, desp, (int) (4.7 * 1024 * 1024), screendshots);
			BasicAppItem b5 = new BasicAppItem("0", "ZenDay", "com.zenday-app",
					0, "1.2", null, 0, iconUrl, 4, 50000,
					downloadUrl, desp, (int) (4.7 * 1024 * 1024), screendshots);
			BasicAppItem b6 = new BasicAppItem("0", "ZenDay", "com.zenday-app",
					0, "1.2", null, 0, iconUrl, 4, 50000,
					downloadUrl, desp, (int) (4.7 * 1024 * 1024), screendshots);
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
				Type type = new TypeToken<List<BasicAppItem>>() {
				}.getType();
				List<BasicAppItem> apps = JsonUtil.fromJsonArray(inputSource,
						type);
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
