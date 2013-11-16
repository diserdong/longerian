package cc.icefire.market.api.parser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import cc.icefire.market.api.response.AppDetailResponse;
import cc.icefire.market.model.BasicAppItem;
import cc.icefire.market.model.Comment;

public class AppDetailParser extends JsonParseHandler<AppDetailResponse> {

	private AppDetailResponse appDetail = new AppDetailResponse();
	
	@Override
	public AppDetailResponse getModel() {
		return appDetail;
	}

	@Override
	public void parse(String inputSource) {
		try {
			BasicAppItem appItem = JsonUtil.fromJsonObject(inputSource, BasicAppItem.class);
			appDetail.setAppItem(appItem);
			appDetail.setSuccess(true);
		} catch (JsonSyntaxException e) {
			appDetail.setSuccess(false);
			e.printStackTrace();
		} catch (JsonParseException e) {
			appDetail.setSuccess(false);
			e.printStackTrace();
		}
	}

}
